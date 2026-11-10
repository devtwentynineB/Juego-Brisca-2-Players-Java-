package uva.tds;

import java.util.List;

/**
 * Fixture de los tests de Puntuacion: reparte las capturas de una partida
 * terminada entre dos jugadores usando la baraja ordenada de BriscaFixture
 * (oros, copas, espadas y bastos; cada palo suma 30 puntos).
 *
 * @author bryaauc
 */
final class PuntuacionFixture {

    private PuntuacionFixture() {
    }

    /**
     * Reparte las 40 cartas como capturas: las primeras n al primer jugador y
     * el resto al segundo. Con n = 20 el reparto es 60-60, con n = 30 es 90-30
     * y con n = 10 es 30-90.
     *
     * @param jugador1 jugador que captura las n primeras cartas
     * @param jugador2 jugador que captura las restantes
     * @param n        número de cartas capturadas por el primer jugador
     */
    static void repartirCapturas(Jugador jugador1, Jugador jugador2, int n) {
        List<Carta> cartas = BriscaFixture.cuarentaCartasOrdenadas();
        capturar(jugador1, cartas.subList(0, n));
        capturar(jugador2, cartas.subList(n, cartas.size()));
    }

    /**
     * Hace que un jugador capture las cartas indicadas, en ese orden.
     *
     * @param jugador jugador que captura
     * @param cartas  cartas capturadas
     */
    static void capturar(Jugador jugador, List<Carta> cartas) {
        for (Carta carta : cartas) {
            jugador.capturar(carta);
        }
    }
}   