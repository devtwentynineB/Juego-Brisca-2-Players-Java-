package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartidaTest {

    private Jugador ana;
    private Jugador luis;
    private Jugador pedro;
    private Partida partida;

    @BeforeEach
    void setUp() {
        ana = new Jugador("Ana");
        luis = new Jugador("Luis");
        pedro = new Jugador("Pedro");
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
}
