package uva.tds;

import java.util.List;
import java.util.Map;

/**
 * Lector de ficheros JSON que describen una partida completa de Brisca.
 *
 * @author bryaauc
 */
final class PartidaJson {

    private PartidaJson() {
    }

    /**
     * Lee un fichero JSON con la partida completa.
     *
     * @param ruta ruta del fichero
     * @return datos de la partida
     * @throws IllegalArgumentException si la ruta es nula, si el fichero no
     *                                  existe o no se puede leer, o si le faltan
     *                                  campos obligatorios
     */
    static DatosPartida leer(String ruta) {
        return new DatosPartida("", "", Map.of(), List.of(), List.of(), null);
    }
}