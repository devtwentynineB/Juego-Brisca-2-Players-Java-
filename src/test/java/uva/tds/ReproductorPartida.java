package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import uva.tds.DatosPartida.Jugada;
import uva.tds.DatosPartida.Resumen;
import uva.tds.DatosPartida.Ronda;

/**
 * Reproduce una partida completa descrita por un fichero JSON sobre Brisca y
 * Puntuacion, verificando en cada baza que las jugadas son válidas y que
 * ganadores, capturas, robos y puntos coinciden con los esperados.
 *
 * @author bryaauc
 */
final class ReproductorPartida {

    private final DatosPartida datos;
    private final Map<String, Jugador> jugadores = new HashMap<>();
    private Brisca brisca;
    private Puntuacion puntuacion;

    private ReproductorPartida(DatosPartida datos) {
        this.datos = datos;
    }

    /**
     * Reproduce la partida y comprueba todos los resultados esperados, incluida
     * la finalización tras 20 bazas, la suma de 120 puntos y el ganador o el
     * empate.
     *
     * @param datos partida leída del fichero
     * @throws AssertionError si alguna jugada, captura, robo, puntuación o el
     *                        resultado final no coincide con lo esperado
     */
    static void reproducir(DatosPartida datos) {
        new ReproductorPartida(datos).ejecutar();
    }

    private void ejecutar() {
        crearPartida();
        verificarReparto();
        for (Ronda ronda : datos.rondas()) {
            verificarInicio(ronda);
            ronda.jugadas().forEach(this::jugar);
            verificarFinal(ronda);
        }
        verificarResumen();
    }

    private void crearPartida() {
        String nombre1 = datos.jugadorInicial();
        String nombre2 = datos.manos().keySet().stream()
                .filter(nombre -> !nombre.equals(nombre1))
                .findFirst()
                .orElseThrow(() -> new AssertionError("El fichero debe tener dos jugadores"));
        Jugador jugador1 = new Jugador(nombre1);
        Jugador jugador2 = new Jugador(nombre2);
        jugadores.put(nombre1, jugador1);
        jugadores.put(nombre2, jugador2);
        brisca = new Brisca(jugador1, jugador2, new Baraja(construirBaraja(nombre1, nombre2)));
        puntuacion = new Puntuacion(jugador1, jugador2);
    }

    private List<Carta> construirBaraja(String nombre1, String nombre2) {
        List<String> textos = new ArrayList<>(datos.manos().get(nombre1));
        textos.addAll(datos.manos().get(nombre2));
        textos.addAll(datos.mazo());
        textos.add(datos.triunfo());
        assertEquals(40, new HashSet<>(textos).size(),
                "La baraja del fichero debe tener 40 cartas distintas");
        return textos.stream().map(CartaTexto::aCarta).toList();
    }

    private void verificarReparto() {
        assertEquals(datos.triunfo(), CartaTexto.de(brisca.getTriunfo()), "Carta de triunfo");
        datos.manos().forEach((nombre, mano) ->
                assertMano("Reparto inicial de " + nombre, mano, jugador(nombre)));
        assertEquals(datos.mazo().size() + 1, brisca.getCartasRestantes(),
                "Cartas restantes tras el reparto");
    }

    private void verificarInicio(Ronda ronda) {
        String contexto = "Baza " + ronda.numero();
        assertEquals(ronda.jugadorInicial(), brisca.getJugadorEnTurno().getNombre(),
                contexto + ": jugador que comienza");
        ronda.manosIniciales().forEach((nombre, mano) ->
                assertMano(contexto + ": mano inicial de " + nombre, mano, jugador(nombre)));
        assertEquals(cartasRestantesAlInicio(ronda), brisca.getCartasRestantes(),
                contexto + ": cartas restantes");
        assertEquals(ronda.numero() - 1, brisca.getBazasDisputadas(),
                contexto + ": bazas disputadas");
    }

    private void jugar(Jugada jugada) {
        Jugador jugador = jugador(jugada.jugador());
        assertEquals(jugada.jugador(), brisca.getJugadorEnTurno().getNombre(),
                "Turno de " + jugada.jugador());
        Carta carta = jugador.getMano().stream()
                .filter(c -> CartaTexto.de(c).equals(jugada.carta()))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        jugada.jugador() + " no tiene la carta " + jugada.carta()));
        brisca.jugarCarta(jugador, carta);
    }

    private void verificarFinal(Ronda ronda) {
        String contexto = "Baza " + ronda.numero();
        verificarCaptura(ronda, contexto);
        verificarRobo(ronda, contexto);
        verificarPuntos(ronda, contexto);
        assertEquals(ronda.numero(), brisca.getBazasDisputadas(),
                contexto + ": bazas disputadas");
        assertEquals(ronda.numero() == datos.rondas().size(), brisca.estaFinalizada(),
                contexto + ": finalización de la partida");
    }

    private void verificarCaptura(Ronda ronda, String contexto) {
        List<Carta> capturadas = jugador(ronda.ganador()).getCartasCapturadas();
        assertTrue(capturadas.size() >= 2, contexto + ": el ganador no ha capturado la baza");
        List<Carta> baza = capturadas.subList(capturadas.size() - 2, capturadas.size());
        assertEquals(ronda.cartasCapturadas(), textos(baza), contexto + ": cartas capturadas");
        assertEquals(ronda.puntosBaza(), baza.stream().mapToInt(Carta::getPuntos).sum(),
                contexto + ": puntos de la baza");
    }

    private void verificarRobo(Ronda ronda, String contexto) {
        for (Jugada robo : ronda.roban()) {
            assertTrue(textos(jugador(robo.jugador()).getMano()).contains(robo.carta()),
                    contexto + ": " + robo.jugador() + " debe haber robado " + robo.carta());
        }
        ronda.manosResultantes().forEach((nombre, mano) ->
                assertMano(contexto + ": mano final de " + nombre, mano, jugador(nombre)));
        assertEquals(cartasRestantesAlInicio(ronda) - ronda.roban().size(),
                brisca.getCartasRestantes(), contexto + ": cartas restantes tras robar");
    }

    private void verificarPuntos(Ronda ronda, String contexto) {
        ronda.puntosAcumulados().forEach((nombre, puntos) ->
                assertEquals(puntos, puntuacion.getPuntos(jugador(nombre)),
                        contexto + ": puntos acumulados de " + nombre));
    }

    private void verificarResumen() {
        Resumen resumen = datos.resumen();
        assertEquals(resumen.totalRondas(), brisca.getBazasDisputadas(), "Bazas disputadas");
        assertTrue(brisca.estaFinalizada(), "La partida debe estar finalizada");
        puntuacion.validarTotal();
        assertEquals(Puntuacion.PUNTOS_TOTALES, puntuacion.getPuntosTotales(),
                "Suma de puntos de ambos jugadores");
        resumen.jugadores().forEach((nombre, esperado) -> {
            Jugador jugador = jugador(nombre);
            assertEquals(esperado.totalPuntos(), puntuacion.getPuntos(jugador),
                    "Puntos totales de " + nombre);
            assertEquals(esperado.cartasTotales(), jugador.getCartasCapturadas().size(),
                    "Cartas capturadas por " + nombre);
            assertEquals(esperado.bazasGanadas(), jugador.getCartasCapturadas().size() / 2,
                    "Bazas ganadas por " + nombre);
        });
        verificarGanador(resumen);
    }

    private void verificarGanador(Resumen resumen) {
        if (resumen.esEmpate()) {
            assertTrue(puntuacion.esEmpate(), "La partida debe terminar en empate");
        } else {
            assertEquals(resumen.ganador(), puntuacion.getGanador().getNombre(),
                    "Ganador de la partida");
        }
    }

    private int cartasRestantesAlInicio(Ronda ronda) {
        return ronda.cartasEnMazo() + (ronda.triunfoDisponible() ? 1 : 0);
    }

    private Jugador jugador(String nombre) {
        Jugador jugador = jugadores.get(nombre);
        assertNotNull(jugador, "Jugador desconocido: " + nombre);
        return jugador;
    }

    private void assertMano(String contexto, List<String> esperada, Jugador jugador) {
        assertEquals(ordenada(esperada), ordenada(textos(jugador.getMano())), contexto);
    }

    private static List<String> textos(List<Carta> cartas) {
        return cartas.stream().map(CartaTexto::de).toList();
    }

    private static List<String> ordenada(List<String> textos) {
        return textos.stream().sorted().toList();
    }
}