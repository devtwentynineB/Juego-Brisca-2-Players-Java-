package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BarajaTest {

    @Test
    public void testCrearBarajaTiene40Cartas() {
        Baraja baraja = new Baraja();

        assertEquals(40, baraja.getCartasRestantes());
    }

    @Test
    public void testCrearBarajaTieneCartaTriunfo() {
        Baraja baraja = new Baraja();

        assertNotNull(baraja.getTriunfo());
    }
}
