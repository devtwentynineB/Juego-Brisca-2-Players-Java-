package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void testRobarCartaDevuelveCartaYDisminuyeRestantes() {
        Baraja baraja = new Baraja();

        Carta carta = baraja.robarCarta();

        assertNotNull(carta);
        assertEquals(39, baraja.getCartasRestantes());
    }

    @Test
    public void testRobarCartaBarajaVaciaLanzaExcepcion() {
        Baraja baraja = new Baraja();

        for (int i = 0; i < 40; i++) {
            baraja.robarCarta();
        }

        assertThrows(IllegalStateException.class, baraja::robarCarta);
    }
}
