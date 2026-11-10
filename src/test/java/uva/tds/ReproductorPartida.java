package uva.tds;

/**
 * Reproduce una partida completa descrita por un fichero JSON sobre Brisca y
 * Puntuacion, verificando en cada baza que las jugadas son válidas y que
 * ganadores, capturas, robos y puntos coinciden con los esperados.
 *
 * @author bryaauc
 */
final class ReproductorPartida {

    private ReproductorPartida() {
    }

    /**
     * Reproduce la partida y comprueba todos los resultados esperados, incluida
     * la finalización tras 20 bazas, la suma de 120 puntos y el ganador o el
     * empate.
     *
     * @param datos partida leída del fichero
     * @throws AssertionError si alguna jugada, captura, robo, puntuación o el
     *                        resultado final no coincide con lo esperado
     */
    static void reproducir(DatosPartida datos) {
        throw new AssertionError("Reproducción pendiente de implementar");
    }
}   