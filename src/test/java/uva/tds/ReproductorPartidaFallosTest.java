package uva.tds;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * Tests de que el reproductor detecta partidas cuyos datos esperados o jugadas
 * no son correctos.
 *
 * @author bryaauc
 */
class ReproductorPartidaFallosTest {

    private static final String RUTA = "src/test/resources/partida_brisca1.json";
    private static final String BAZA_1 = "/partida/rondas/0/final";
    private static final String JUGADA_1 = "/partida/rondas/0/jugadas/0";

    @TempDir
    Path directorio;

    private String copiaModificada(String puntero, String campo, JsonNode valor)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode raiz = mapper.readTree(new File(RUTA));
        ((ObjectNode) raiz.at(puntero)).set(campo, valor);
        Path destino = directorio.resolve("modificada.json");
        mapper.writeValue(destino.toFile(), raiz);
        return destino.toString();
    }

    private void assertReproduccionFalla(String ruta) {
        assertThrows(AssertionError.class,
                () -> ReproductorPartida.reproducir(PartidaJson.leer(ruta)));
    }

    @Test
    void ganadorDeBazaIncorrectoSeDetecta() throws IOException {
        assertReproduccionFalla(copiaModificada(BAZA_1, "ganador", TextNode.valueOf("Luis")));
    }

    @Test
    void puntosDeBazaIncorrectosSeDetectan() throws IOException {
        assertReproduccionFalla(copiaModificada(BAZA_1, "puntos_baza", IntNode.valueOf(7)));
    }

    @Test
    void cartaQueNoEstaEnLaManoSeDetecta() throws IOException {
        assertReproduccionFalla(copiaModificada(JUGADA_1, "juega", TextNode.valueOf("1-oros")));
    }

    @Test
    void jugadaFueraDeTurnoSeDetecta() throws IOException {
        assertReproduccionFalla(copiaModificada(JUGADA_1, "jugador", TextNode.valueOf("Luis")));
    }

    @Test
    void puntuacionFinalIncorrectaSeDetecta() throws IOException {
        assertReproduccionFalla(copiaModificada(
                "/partida/resumen_partida/resumen_por_jugador/Ana", "total_puntos",
                IntNode.valueOf(41)));
    }
}
