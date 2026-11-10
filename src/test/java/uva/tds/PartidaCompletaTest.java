package uva.tds;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test de sistema: reproduce partidas completas descritas en ficheros JSON.
 *
 * @author bryaauc
 */
class PartidaCompletaTest {

    /**
     * Reproduce la partida del fichero indicado y verifica jugadas, capturas,
     * robos, puntuaciones y resultado final.
     *
     * @param ruta ruta del fichero JSON de la partida
     */
    @ParameterizedTest(name = "testPartidaCompleta({0})")
    @ValueSource(strings = { "src/test/resources/partida_brisca1.json" })
    void testPartidaCompleta(String ruta) {
        ReproductorPartida.reproducir(PartidaJson.leer(ruta));
    }
}
