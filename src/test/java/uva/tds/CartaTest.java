package uva.tds;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CartaTest {

    @Test
    public void testCrearCartaValida() {
        Carta carta = new Carta(Palo.OROS, 1);

        assertEquals(1, carta.getValor());
        assertEquals(Palo.OROS, carta.getPalo());
    }

    @Test
    public void testCrearCartaValorOchoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Carta(Palo.OROS, 8));
    }

    @Test
    public void testCrearCartaValorNueveLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Carta(Palo.OROS, 9));
    }

    @Test
    public void testCrearCartaValorFueraDeRangoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Carta(Palo.OROS, 0));
        assertDoesNotThrow(() -> new Carta(Palo.OROS, 1));

        assertDoesNotThrow(() -> new Carta(Palo.OROS, 7));
        assertThrows(IllegalArgumentException.class, () -> new Carta(Palo.OROS, 8));

        assertThrows(IllegalArgumentException.class, () -> new Carta(Palo.OROS, 9));
        assertDoesNotThrow(() -> new Carta(Palo.OROS, 10));

        assertDoesNotThrow(() -> new Carta(Palo.OROS, 12));
        assertThrows(IllegalArgumentException.class, () -> new Carta(Palo.OROS, 13));
    }
}
