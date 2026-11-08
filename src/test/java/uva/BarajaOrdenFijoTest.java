package uva;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uva.tds.Baraja;
import uva.tds.Carta;
import uva.tds.Palo;

/**
 * Tests del constructor de Baraja con un orden de cartas predefinido.
 *
 * @author giarave
 */
class BarajaOrdenFijoTest {

    private List<Carta> cuarentaCartas;

    @BeforeEach
    void setUp() {
        cuarentaCartas = new ArrayList<>();
        int[] valores = { 1, 2, 3, 4, 5, 6, 7, 10, 11, 12 };
        for (Palo palo : Palo.values()) {
            for (int valor : valores) {
                cuarentaCartas.add(new Carta(palo, valor));
            }
        }
    }

    @Test
    void crearBarajaConListaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Baraja(null));
    }

    @Test
    void crearBarajaConTreintaYNueveCartasLanzaExcepcion() {
        cuarentaCartas.remove(0);
        assertThrows(IllegalArgumentException.class, () -> new Baraja(cuarentaCartas));
    }

    @Test
    void crearBarajaConCuarentaYUnaCartasLanzaExcepcion() {
        cuarentaCartas.add(new Carta(Palo.OROS, 1));
        assertThrows(IllegalArgumentException.class, () -> new Baraja(cuarentaCartas));
    }

    @Test
    void crearBarajaConUnaCartaNulaLanzaExcepcion() {
        cuarentaCartas.set(10, null);
        assertThrows(IllegalArgumentException.class, () -> new Baraja(cuarentaCartas));
    }

    @Test
    void barajaConOrdenFijoTiene40Cartas() {
        Baraja baraja = new Baraja(cuarentaCartas);
        assertEquals(40, baraja.getCartasRestantes());
    }

    @Test
    void robarCartaDevuelveLasCartasEnElOrdenDeLaLista() {
        Baraja baraja = new Baraja(cuarentaCartas);
        assertSame(cuarentaCartas.get(0), baraja.robarCarta());
        assertSame(cuarentaCartas.get(1), baraja.robarCarta());
        assertEquals(38, baraja.getCartasRestantes());
    }

    @Test
    void elTriunfoEsLaUltimaCartaDeLaLista() {
        Baraja baraja = new Baraja(cuarentaCartas);
        assertSame(cuarentaCartas.get(39), baraja.getTriunfo());
    }

    @Test
    void elTriunfoEsLaUltimaCartaEnRobarse() {
        Baraja baraja = new Baraja(cuarentaCartas);
        for (int i = 0; i < 39; i++) {
            baraja.robarCarta();
        }
        assertSame(baraja.getTriunfo(), baraja.robarCarta());
    }

    @Test
    void modificarLaListaOriginalNoAfectaALaBaraja() {
        Baraja baraja = new Baraja(cuarentaCartas);
        Carta primera = cuarentaCartas.get(0);
        cuarentaCartas.clear();
        assertEquals(40, baraja.getCartasRestantes());
        assertSame(primera, baraja.robarCarta());
    }
}