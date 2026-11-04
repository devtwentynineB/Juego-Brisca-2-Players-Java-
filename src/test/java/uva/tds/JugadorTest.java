package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
