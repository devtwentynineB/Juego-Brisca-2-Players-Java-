package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import uva.tds.DatosPartida.Jugada;
import uva.tds.DatosPartida.Ronda;

/**
 * Tests de la lectura del fichero JSON de la partida 1.
 *
 * @author bryaauc
 */
class PartidaJsonTest {

    private static final String RUTA = "src/test/resources/partida_brisca1.json";

    private DatosPartida datos;

    @BeforeEach
    void setUp() {
        datos = PartidaJson.leer(RUTA);
    }

    @Nested
    class CasosNoValidos {

        @Test
        void rutaNulaLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> PartidaJson.leer(null));
        }

        @Test
        void ficheroInexistenteLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class,
                    () -> PartidaJson.leer("src/test/resources/no_existe.json"));
        }
    }

    @Nested
    class InicioDeLaPartida {

        @Test
        void comienzaAna() {
            assertEquals("Ana", datos.jugadorInicial());
        }

        @Test
        void elTriunfoEsElCincoDeBastos() {
            assertEquals("5-bastos", datos.triunfo());
        }

        @Test
        void seLeenLasManosIniciales() {
            assertEquals(List.of("11-espadas", "11-bastos", "7-espadas"), datos.manos().get("Ana"));
            assertEquals(List.of("7-bastos", "6-copas", "12-bastos"), datos.manos().get("Luis"));
        }

        @Test
        void elMazoTiene33CartasEnSuOrden() {
            assertEquals(33, datos.mazo().size());
            assertEquals("1-espadas", datos.mazo().get(0));
            assertEquals("3-espadas", datos.mazo().get(32));
        }
    }

    @Nested
    class Rondas {

        @Test
        void hay20Rondas() {
            assertEquals(20, datos.rondas().size());
        }

        @Test
        void seLeeElInicioDeLaPrimeraRonda() {
            Ronda ronda = datos.rondas().get(0);
            assertEquals(1, ronda.numero());
            assertEquals("Ana", ronda.jugadorInicial());
            assertEquals(33, ronda.cartasEnMazo());
            assertTrue(ronda.triunfoDisponible());
            assertEquals(List.of("11-bastos", "11-espadas", "7-espadas"),
                    ronda.manosIniciales().get("Ana"));
        }

        @Test
        void seLeenLasJugadasDeLaPrimeraRonda() {
            assertEquals(List.of(new Jugada("Ana", "11-espadas"), new Jugada("Luis", "6-copas")),
                    datos.rondas().get(0).jugadas());
        }

        @Test
        void seLeeElResultadoDeLaPrimeraRonda() {
            Ronda ronda = datos.rondas().get(0);
            assertEquals("Ana", ronda.ganador());
            assertEquals(List.of("11-espadas", "6-copas"), ronda.cartasCapturadas());
            assertEquals(3, ronda.puntosBaza());
            assertEquals(List.of(new Jugada("Luis", "1-espadas"), new Jugada("Ana", "10-copas")),
                    ronda.roban());
            assertEquals(Map.of("Ana", 3, "Luis", 0), ronda.puntosAcumulados());
        }

        @Test
        void enLaUltimaRondaNoQuedanCartasNiRobo() {
            Ronda ronda = datos.rondas().get(19);
            assertEquals(20, ronda.numero());
            assertEquals(0, ronda.cartasEnMazo());
            assertFalse(ronda.triunfoDisponible());
            assertEquals(List.of(), ronda.roban());
        }
    }

    @Nested
    class ResumenFinal {

        @Test
        void seLeeElGanadorYLosPuntos() {
            DatosPartida.Resumen resumen = datos.resumen();
            assertEquals(20, resumen.totalRondas());
            assertEquals("Luis", resumen.ganador());
            assertEquals(40, resumen.jugadores().get("Ana").totalPuntos());
            assertEquals(80, resumen.jugadores().get("Luis").totalPuntos());
        }

        @Test
        void conGanadorNoEsEmpate() {
            assertFalse(datos.resumen().esEmpate());
        }
    }
}