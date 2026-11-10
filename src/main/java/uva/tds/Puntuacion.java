package uva.tds;

/**
 * Puntuación de una partida de la Brisca simplificada entre dos jugadores.
 *
 * <p>
 * La puntuación de cada jugador es la suma de los puntos de las cartas que ha
 * capturado. Los puntos se calculan en el momento de consultarlos, por lo que
 * reflejan siempre el estado actual de los jugadores. La baraja suma 120
 * puntos en total, de modo que el resultado final de una partida solo está
 * disponible cuando los dos jugadores suman exactamente 120 puntos. Si ambos
 * obtienen 60 puntos la partida termina en empate.
 * </p>
 *
 * @author tu_usuario
 */
public class Puntuacion {

    /** Suma de los puntos de las 40 cartas de la baraja. */
    public static final int PUNTOS_TOTALES = 120;

    /**
     * Crea la puntuación de los dos jugadores indicados.
     *
     * @param jugador1 primer jugador
     * @param jugador2 segundo jugador
     * @throws IllegalArgumentException si algún jugador es nulo o si ambos son
     *                                  el mismo jugador
     */
    public Puntuacion(Jugador jugador1, Jugador jugador2) {
    }

    /**
     * Obtiene los puntos de un jugador: la suma de los puntos de todas las
     * cartas que ha capturado hasta el momento.
     *
     * @param jugador jugador del que se quieren los puntos
     * @return puntos del jugador
     * @throws IllegalArgumentException si el jugador es nulo o no es ninguno de
     *                                  los dos jugadores de esta puntuación
     */
    public int getPuntos(Jugador jugador) {
        return 0;
    }

    /**
     * Obtiene la suma de los puntos de ambos jugadores.
     *
     * @return puntos de los dos jugadores sumados
     */
    public int getPuntosTotales() {
        return 0;
    }

    /**
     * Comprueba que los puntos de ambos jugadores suman exactamente 120, lo que
     * ocurre al terminar una partida completa en la que se han capturado las 40
     * cartas.
     *
     * @throws IllegalStateException si la suma de los puntos no es 120
     */
    public void validarTotal() {
    }

    /**
     * Indica si la partida ha terminado en empate, es decir, si ambos jugadores
     * tienen los mismos puntos (60 cada uno).
     *
     * @return true si hay empate; false si uno de los jugadores gana
     * @throws IllegalStateException si la suma de los puntos no es 120
     */
    public boolean esEmpate() {
        return false;
    }

    /**
     * Obtiene el jugador ganador, que es el que tiene más puntos.
     *
     * @return jugador ganador
     * @throws IllegalStateException si la suma de los puntos no es 120 o si la
     *                               partida ha terminado en empate
     */
    public Jugador getGanador() {
        return null;
    }
}