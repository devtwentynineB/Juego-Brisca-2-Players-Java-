package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class PartidaTest {

    private Jugador ana;
    private Jugador luis;
    private Jugador pedro;
    private Carta cartaAna;
    private Carta cartaLuis;
    private Partida partida;

    @BeforeEach
    void setUp() {
        ana = new Jugador("Ana");
        luis = new Jugador("Luis");
        pedro = new Jugador("Pedro");
        cartaAna = new Carta(Palo.COPAS, 1);
        cartaLuis = new Carta(Palo.ESPADAS, 3);
        ana.recibirCarta(cartaAna);
        luis.recibirCarta(cartaLuis);
        partida = new Partida(ana, luis);
    }

    @Test
    void crearPartidaConPrimerJugadorNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Partida(null, luis));
    }

    @Test
    void crearPartidaConSegundoJugadorNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Partida(ana, null));
    }

    @Test
    void crearPartidaConElMismoJugadorDosVecesLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Partida(ana, ana));
    }

    @Test
    void crearPartidaValidaGuardaLosDosJugadores() {
        Partida partida = new Partida(ana, luis);
        assertEquals(ana, partida.getJugador1());
        assertEquals(luis, partida.getJugador2());
    }

    @Test
    void enUnaPartidaNuevaComienzaElPrimerJugador() {
        Partida partida = new Partida(ana, luis);
        assertEquals(ana, partida.getJugadorEnTurno());
    }

    @Test
    void jugarCartaConJugadorNuloLanzaExcepcion() {
        Carta carta = new Carta(Palo.COPAS, 1);
        assertThrows(IllegalArgumentException.class,
                () -> partida.JugarCarta(null, carta));
    }

    @Test
    void jugarCartaConCartaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> partida.JugarCarta(ana, null));
    }

    @Test
    void jugarCartaDeUnJugadorAjenoALaPartidaLanzaExcepcion() {
        Carta carta = new Carta(Palo.COPAS, 1);
        pedro.recibirCarta(carta);
        assertThrows(IllegalArgumentException.class,
                () -> partida.JugarCarta(pedro, carta));
    }

    @Test
    void jugarFueraDeTurnoLanzaExcepcion() {
        assertThrows(IllegalStateException.class,
                () -> partida.JugarCarta(luis, cartaLuis));
    }

    @Test
    void jugarFueraDeTurnoNoModificaLaManoDelJugador() {
        assertThrows(IllegalStateException.class,
                () -> partida.JugarCarta(luis, cartaLuis));
        assertEquals(List.of(cartaLuis), luis.getMano());
    }

    @Test
    void jugarUnaCartaQueNoEstaEnLaManoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> partida.JugarCarta(ana, cartaLuis));
    }

    @Test
    void jugarUnaCartaQueNoEstaEnLaManoNoModificaLaMano() {
        assertThrows(IllegalArgumentException.class,
                () -> partida.JugarCarta(ana, cartaLuis));
        assertEquals(List.of(cartaAna), ana.getMano());
    }

    @Test
    void jugarUnaCartaValidaLaQuitaDeLaManoDelJugador() {
        partida.JugarCarta(ana, cartaAna);
        assertEquals(List.of(), ana.getMano());
    }

    @Test
    void trasJugarElPrimerJugadorElTurnoEsDelOtro() {
        partida.JugarCarta(ana, cartaAna);
        assertEquals(luis, partida.getJugadorEnTurno());
    }

    @Test
    void elSegundoJugadorPuedeJugarTrasElPrimero() {
        partida.JugarCarta(ana, cartaAna);
        assertDoesNotThrow(() -> partida.JugarCarta(luis, cartaLuis));
    }

    @Test
    void elMismoJugadorNoPuedeJugarDosVecesSeguidas() {
        Carta otraCarta = new Carta(Palo.BASTOS, 4);
        ana.recibirCarta(otraCarta);
        partida.JugarCarta(ana, cartaAna);
        assertThrows(IllegalStateException.class,
                () -> partida.JugarCarta(ana, otraCarta));
    }
}
