package uva.tds;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests de la validación del total de 120 puntos, el empate y el ganador.
 *
 * @author tu_usuario
 */
class PuntuacionResultadoTest {

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
    class PartidaSinTerminar {
        @Test
    void sinCapturasValidarTotalLanzaExcepcion() {
        assertThrows(IllegalStateException.class, () -> puntuacion.validarTotal());
    }

    @Test
    void conSolo109PuntosValidarTotalLanzaExcepcion() {
        List<Carta> cartas = BriscaFixture.cuarentaCartasOrdenadas();
        cartas.remove(0); // as de oros: 11 puntos -> total 109
        PuntuacionFixture.capturar(ana, cartas);
        assertEquals(109, puntuacion.getPuntosTotales());
        assertThrows(IllegalStateException.class, () -> puntuacion.validarTotal());
    }

    @Test
    void sinTerminarConsultarEmpateLanzaExcepcion() {
        ana.capturar(new Carta(Palo.OROS, 1));
        assertThrows(IllegalStateException.class, () -> puntuacion.esEmpate());
    }

    @Test
    void sinTerminarConsultarGanadorLanzaExcepcion() {
        ana.capturar(new Carta(Palo.OROS, 1));
        assertThrows(IllegalStateException.class, () -> puntuacion.getGanador());
    }

       
    }

    @Nested
    class PartidaConGanador {

        @BeforeEach
        void ganaAna() {
            PuntuacionFixture.repartirCapturas(ana, luis, 30); // 90 - 30
        }

        @Test
        void losPuntosTotalesSon120() {
            assertEquals(120, puntuacion.getPuntosTotales());
        }

        @Test
        void validarTotalNoLanzaExcepcion() {
            assertDoesNotThrow(() -> puntuacion.validarTotal());
        }

        @Test
        void cadaJugadorTieneSusPuntos() {
            assertEquals(90, puntuacion.getPuntos(ana));
            assertEquals(30, puntuacion.getPuntos(luis));
        }

        @Test
        void noEsEmpate() {
            assertFalse(puntuacion.esEmpate());
        }

        @Test
        void ganaElJugadorConMasPuntos() {
            assertSame(ana, puntuacion.getGanador());
        }
    }

    @Nested
    class PartidaConVictoriaDelSegundo {

        @Test
        void ganaElSegundoJugadorSiTieneMasPuntos() {
            PuntuacionFixture.repartirCapturas(ana, luis, 10); // 30 - 90
            assertSame(luis, puntuacion.getGanador());
        }

        
    }

    @Nested
    class PartidaConEmpate {

        @BeforeEach
        void empatan() {
            PuntuacionFixture.repartirCapturas(ana, luis, 20); // 60 - 60
        }

        @Test
        void ambosJugadoresTienen60Puntos() {
            assertEquals(60, puntuacion.getPuntos(ana));
            assertEquals(60, puntuacion.getPuntos(luis));
        }

        @Test
        void validarTotalNoLanzaExcepcion() {
            assertDoesNotThrow(() -> puntuacion.validarTotal());
        }

        @Test
        void esEmpate() {
            assertTrue(puntuacion.esEmpate());
        }

        @Test
        void enEmpateNoHayGanadorYLanzaExcepcion() {
            assertThrows(IllegalStateException.class, () -> puntuacion.getGanador());
        }
    }
}