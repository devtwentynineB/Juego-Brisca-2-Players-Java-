package uva.tds;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
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

    @Test
    public void testObtenerPuntosCarta() {
        assertAll(
                () -> assertEquals(11, new Carta(Palo.OROS, 1).getPuntos()),
                () -> assertEquals(0, new Carta(Palo.OROS, 2).getPuntos()),
                () -> assertEquals(10, new Carta(Palo.OROS, 3).getPuntos()),
                () -> assertEquals(0, new Carta(Palo.OROS, 4).getPuntos()),
                () -> assertEquals(0, new Carta(Palo.OROS, 5).getPuntos()),
                () -> assertEquals(0, new Carta(Palo.OROS, 6).getPuntos()),
                () -> assertEquals(0, new Carta(Palo.OROS, 7).getPuntos()),
                () -> assertEquals(2, new Carta(Palo.OROS, 10).getPuntos()),
                () -> assertEquals(3, new Carta(Palo.OROS, 11).getPuntos()),
                () -> assertEquals(4, new Carta(Palo.OROS, 12).getPuntos()));
    }

    @Test
    public void testObtenerJerarquiaCarta() {
        assertAll(
                () -> assertEquals(1, new Carta(Palo.OROS, 1).getJerarquia()),
                () -> assertEquals(10, new Carta(Palo.OROS, 2).getJerarquia()),
                () -> assertEquals(2, new Carta(Palo.OROS, 3).getJerarquia()),
                () -> assertEquals(9, new Carta(Palo.OROS, 4).getJerarquia()),
                () -> assertEquals(8, new Carta(Palo.OROS, 5).getJerarquia()),
                () -> assertEquals(7, new Carta(Palo.OROS, 6).getJerarquia()),
                () -> assertEquals(6, new Carta(Palo.OROS, 7).getJerarquia()),
                () -> assertEquals(5, new Carta(Palo.OROS, 10).getJerarquia()),
                () -> assertEquals(4, new Carta(Palo.OROS, 11).getJerarquia()),
                () -> assertEquals(3, new Carta(Palo.OROS, 12).getJerarquia()));
    }
}
