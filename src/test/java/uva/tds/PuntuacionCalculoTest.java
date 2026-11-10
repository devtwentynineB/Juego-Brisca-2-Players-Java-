package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests de la creación de Puntuacion y del cálculo de puntos de cada jugador.
 *
 * @author tu_usuario
 */
class PuntuacionCalculoTest {

    private Jugador ana;
    private Jugador luis;
    private Puntuacion puntuacion;

    @BeforeEach
    void setUp() {
        ana = new Jugador("Ana");
        luis = new Jugador("Luis");
        puntuacion = new Puntuacion(ana, luis);
    }

    @Nested
    class CasosNoValidos {

        @Test
        void primerJugadorNuloLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Puntuacion(null, luis));
        }

        @Test
        void segundoJugadorNuloLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Puntuacion(ana, null));
        }

        @Test
        void mismoJugadorDosVecesLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Puntuacion(ana, ana));
        }

        @Test
        void puntosDeJugadorNuloLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> puntuacion.getPuntos(null));
        }

        @Test
        void puntosDeJugadorAjenoLanzaExcepcion() {
            Jugador pedro = new Jugador("Pedro");
            assertThrows(IllegalArgumentException.class, () -> puntuacion.getPuntos(pedro));
        }
    }

    @Nested
    class CasosValidos {

        @Test
        void jugadorSinCapturasTieneCeroPuntos() {
            assertEquals(0, puntuacion.getPuntos(ana));
        }

        @Test
        void unaCartaSinPuntosSumaCero() {
            ana.capturar(new Carta(Palo.OROS, 2));
            assertEquals(0, puntuacion.getPuntos(ana));
        }

        @Test
        void unAsSuma11Puntos() {
            ana.capturar(new Carta(Palo.OROS, 1));
            assertEquals(11, puntuacion.getPuntos(ana));
        }

        @Test
        void variasCartasSumanSusPuntos() {
            PuntuacionFixture.capturar(ana, List.of(
                    new Carta(Palo.COPAS, 1), new Carta(Palo.COPAS, 3),
                    new Carta(Palo.COPAS, 12), new Carta(Palo.COPAS, 11),
                    new Carta(Palo.COPAS, 10)));
            assertEquals(30, puntuacion.getPuntos(ana));
        }

        @Test
        void losPuntosNoDependenDelPalo() {
            ana.capturar(new Carta(Palo.OROS, 3));
            ana.capturar(new Carta(Palo.BASTOS, 3));
            assertEquals(20, puntuacion.getPuntos(ana));
        }

        @Test
        void cadaJugadorSumaSoloSusCapturas() {
            ana.capturar(new Carta(Palo.OROS, 1));
            luis.capturar(new Carta(Palo.COPAS, 3));
            assertEquals(11, puntuacion.getPuntos(ana));
            assertEquals(10, puntuacion.getPuntos(luis));
        }

        @Test
        void lasCapturasPosterioresSeReflejanEnLosPuntos() {
            ana.capturar(new Carta(Palo.OROS, 1));
            assertEquals(11, puntuacion.getPuntos(ana));
            ana.capturar(new Carta(Palo.OROS, 3));
            assertEquals(21, puntuacion.getPuntos(ana));
        }

        @Test
        void calcularPuntosNoModificaLasCapturas() {
            ana.capturar(new Carta(Palo.OROS, 1));
            puntuacion.getPuntos(ana);
            assertEquals(1, ana.getCartasCapturadas().size());
        }

        @Test
        void sinCapturasLosPuntosTotalesSonCero() {
            assertEquals(0, puntuacion.getPuntosTotales());
        }

        @Test
        void losPuntosTotalesSumanLosDeAmbosJugadores() {
            ana.capturar(new Carta(Palo.OROS, 1));
            luis.capturar(new Carta(Palo.COPAS, 3));
            assertEquals(21, puntuacion.getPuntosTotales());
        }
    }
}