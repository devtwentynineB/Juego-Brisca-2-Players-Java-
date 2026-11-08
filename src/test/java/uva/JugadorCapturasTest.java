package uva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uva.tds.Carta;
import uva.tds.Jugador;
import uva.tds.Palo;

/**
 * Tests de las cartas capturadas por un jugador.
 *
 * @author giarave
 */
class JugadorCapturasTest {

    private Jugador ana;
    private Carta asOros;
    private Carta tresCopas;

    @BeforeEach
    void setUp() {
        ana = new Jugador("Ana");
        asOros = new Carta(Palo.OROS, 1);
        tresCopas = new Carta(Palo.COPAS, 3);
    }

    @Test
    void jugadorNuevoNoHaCapturadoCartas() {
        assertEquals(List.of(), ana.getCartasCapturadas());
    }

    @Test
    void capturarUnaCartaLaAnadeALasCapturadas() {
        ana.capturar(asOros);
        assertEquals(List.of(asOros), ana.getCartasCapturadas());
    }

    @Test
    void capturarVariasCartasMantieneElOrden() {
        ana.capturar(asOros);
        ana.capturar(tresCopas);
        assertEquals(List.of(asOros, tresCopas), ana.getCartasCapturadas());
    }

    @Test
    void capturarUnaCartaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> ana.capturar(null));
    }

    @Test
    void capturarUnaCartaNoModificaLaMano() {
        ana.capturar(asOros);
        assertEquals(List.of(), ana.getMano());
    }

    @Test
    void modificarLaListaDevueltaNoAlteraLasCapturadas() {
        ana.capturar(asOros);
        ana.getCartasCapturadas().clear();
        assertEquals(List.of(asOros), ana.getCartasCapturadas());
    }
}
