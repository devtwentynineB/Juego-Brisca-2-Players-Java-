package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JugadorTest {

    @Test
    public void testCrearJugadorValido() {
        Jugador jugador = new Jugador("Alice");

        assertEquals("Alice", jugador.getNombre());
    }

    @Test
    public void testCrearJugadorNombreNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador(null));
    }

    @Test
    public void testCrearJugadorNombreVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador(""));
        assertThrows(IllegalArgumentException.class, () -> new Jugador("   "));
    }

    @Test
    public void testRecibirCartaAñadeCartaALaMano() {
        Jugador jugador = new Jugador("Alice");
        Carta carta = new Carta(Palo.OROS, 1);

        jugador.recibirCarta(carta);

        assertEquals(1, jugador.getMano().size());
        assertTrue(jugador.getMano().contains(carta));
    }

    @Test
    public void testJugarCartaEliminaCartaDeLaMano() {
        Jugador jugador = new Jugador("Alice");
        Carta carta = new Carta(Palo.OROS, 1);
        jugador.recibirCarta(carta);

        Carta cartaJugadas = jugador.jugarCarta(carta);

        assertEquals(carta, cartaJugadas);
        assertEquals(0, jugador.getMano().size());
    }

    @Test
    public void testJugarCartaNoEnManoLanzaExcepcion() {
        Jugador jugador = new Jugador("Alice");
        Carta cartaEnMano = new Carta(Palo.OROS, 1);
        Carta cartaNoEnMano = new Carta(Palo.COPAS, 3);
        jugador.recibirCarta(cartaEnMano);

        assertThrows(IllegalArgumentException.class, () -> jugador.jugarCarta(cartaNoEnMano));
    }
}
