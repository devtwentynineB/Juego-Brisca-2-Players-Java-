package uva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uva.tds.Baraja;
import uva.tds.Carta;
import uva.tds.Jugador;
import uva.tds.Partida;

/**
 * Tests de la creación de una Partida con baraja.
 *
 * @author giarave
 */
class PartidaConBarajaTest {

    private Jugador ana;
    private Jugador luis;
    private List<Carta> cartas;
    private Baraja baraja;

    @BeforeEach
    void setUp() {
        ana = new Jugador("Ana");
        luis = new Jugador("Luis");
        cartas = BarajaFixture.cuarentaCartasOrdenadas();
        baraja = new Baraja(cartas);
    }

    @Test
    void crearPartidaConBarajaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Partida(ana, luis, null));
    }

    @Test
    void crearPartidaConBarajaYJugadorNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Partida(null, luis, baraja));
    }

    @Test
    void partidaConBarajaConservaLasCartasDeLaBaraja() {
        Partida partida = new Partida(ana, luis, baraja);
        assertEquals(40, partida.getCartasRestantes());
    }

    @Test
    void elTriunfoDeLaPartidaEsElDeLaBaraja() {
        Partida partida = new Partida(ana, luis, baraja);
        assertSame(cartas.get(39), partida.getTriunfo());
    }

    @Test
    void partidaConBarajaComienzaElPrimerJugador() {
        Partida partida = new Partida(ana, luis, baraja);
        assertEquals(ana, partida.getJugadorEnTurno());
    }

    @Test
    void partidaSinBarajaExplicitaCreaUnaBarajaDe40Cartas() {
        Partida partida = new Partida(ana, luis);
        assertEquals(40, partida.getCartasRestantes());
    }
}
