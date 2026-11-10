package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests de la creación de Brisca: reparto inicial y selección del triunfo.
 *
 * @author bryaauc
 */
class BriscaCreacionTest {

    private Jugador ana;
    private Jugador luis;
    private List<Carta> cartas;
    private Baraja baraja;

    @BeforeEach
    void setUp() {
        ana = new Jugador("Ana");
        luis = new Jugador("Luis");
        cartas = BriscaFixture.cuarentaCartasOrdenadas();
        baraja = new Baraja(cartas);
    }

    @Nested
    class CasosNoValidos {

        @Test
        void primerJugadorNuloLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Brisca(null, luis, baraja));
        }

        @Test
        void segundoJugadorNuloLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Brisca(ana, null, baraja));
        }

        @Test
        void mismoJugadorDosVecesLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Brisca(ana, ana, baraja));
        }

        @Test
        void barajaNulaLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Brisca(ana, luis, null));
        }

        @Test
        void barajaYaUsadaLanzaExcepcion() {
            baraja.robarCarta();
            assertThrows(IllegalArgumentException.class, () -> new Brisca(ana, luis, baraja));
        }

        @Test
        void primerJugadorConCartasEnLaManoLanzaExcepcion() {
            ana.recibirCarta(new Carta(Palo.OROS, 1));
            assertThrows(IllegalArgumentException.class, () -> new Brisca(ana, luis, baraja));
        }

        @Test
        void segundoJugadorConCartasEnLaManoLanzaExcepcion() {
            luis.recibirCarta(new Carta(Palo.OROS, 1));
            assertThrows(IllegalArgumentException.class, () -> new Brisca(ana, luis, baraja));
        }

        @Test
        void siLaCreacionFallaNoSeRepartenCartas() {
            luis.recibirCarta(new Carta(Palo.OROS, 1));
            assertThrows(IllegalArgumentException.class, () -> new Brisca(ana, luis, baraja));
            assertEquals(List.of(), ana.getMano());
            assertEquals(40, baraja.getCartasRestantes());
        }

        @Test
        void sinBarajaExplicitaConJugadorNuloLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Brisca(null, luis));
        }

        @Test
        void sinBarajaExplicitaConMismoJugadorLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Brisca(ana, ana));
        }
    }

    @Nested
    class CasosValidos {

        @Test
        void seGuardanLosDosJugadores() {
            Brisca brisca = new Brisca(ana, luis, baraja);
            assertSame(ana, brisca.getJugador1());
            assertSame(luis, brisca.getJugador2());
        }

        @Test
        void elPrimerJugadorRecibeSusTresPrimerasCartas() {
            new Brisca(ana, luis, baraja);
            assertEquals(List.of(cartas.get(0), cartas.get(1), cartas.get(2)), ana.getMano());
        }

        @Test
        void elSegundoJugadorRecibeLasTresCartasSiguientes() {
            new Brisca(ana, luis, baraja);
            assertEquals(List.of(cartas.get(3), cartas.get(4), cartas.get(5)), luis.getMano());
        }

        @Test
        void trasElRepartoQuedan34CartasEnLaBaraja() {
            Brisca brisca = new Brisca(ana, luis, baraja);
            assertEquals(34, brisca.getCartasRestantes());
        }

        @Test
        void laCartaDeTriunfoEsLaUltimaDeLaBaraja() {
            Brisca brisca = new Brisca(ana, luis, baraja);
            assertSame(cartas.get(39), brisca.getTriunfo());
        }

        @Test
        void elPaloDeTriunfoEsElDeLaCartaDeTriunfo() {
            Brisca brisca = new Brisca(ana, luis, baraja);
            assertEquals(Palo.BASTOS, brisca.getPaloTriunfo());
        }

        @Test
        void laCartaDeTriunfoNoSeRepartePorAdelantado() {
            Brisca brisca = new Brisca(ana, luis, baraja);
            assertFalse(ana.getMano().contains(brisca.getTriunfo()));
            assertFalse(luis.getMano().contains(brisca.getTriunfo()));
        }

        @Test
        void comienzaElPrimerJugador() {
            Brisca brisca = new Brisca(ana, luis, baraja);
            assertSame(ana, brisca.getJugadorEnTurno());
        }

        @Test
        void alCrearseNadieHaCapturadoCartas() {
            new Brisca(ana, luis, baraja);
            assertEquals(List.of(), ana.getCartasCapturadas());
            assertEquals(List.of(), luis.getCartasCapturadas());
        }

        @Test
        void sinBarajaExplicitaSeRepartenTresCartasACadaJugador() {
            Brisca brisca = new Brisca(ana, luis);
            assertEquals(3, ana.getMano().size());
            assertEquals(3, luis.getMano().size());
            assertEquals(34, brisca.getCartasRestantes());
        }

        @Test
        void sinBarajaExplicitaElPaloDeTriunfoEsElDeLaCartaDeTriunfo() {
            Brisca brisca = new Brisca(ana, luis);
            assertNotNull(brisca.getTriunfo());
            assertEquals(brisca.getTriunfo().getPalo(), brisca.getPaloTriunfo());
        }
    }
}