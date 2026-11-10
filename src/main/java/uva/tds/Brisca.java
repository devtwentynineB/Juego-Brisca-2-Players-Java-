package uva.tds;

/**
 * Partida de la Brisca simplificada para dos jugadores, lista para jugar.
 *
 * <p>
 * Al crearla se reparten 3 cartas a cada jugador (primero al primer jugador y
 * después al segundo) y la última carta de la baraja queda reservada como
 * carta de triunfo, que será la última en entregarse. Comienza la primera baza
 * el primer jugador.
 * </p>
 *
 * @author tu_usuario
 */
public class Brisca {

    /**
     * Crea una partida con una baraja mezclada al azar y reparte las cartas
     * iniciales.
     *
     * @param jugador1 primer jugador, que comienza la primera baza
     * @param jugador2 segundo jugador
     * @throws IllegalArgumentException si algún jugador es nulo, si ambos son el
     *                                  mismo o si alguno tiene cartas en la mano
     */
    public Brisca(Jugador jugador1, Jugador jugador2) {
    }

    /**
     * Crea una partida con la baraja indicada y reparte las cartas iniciales.
     * Si la creación falla no se reparte ninguna carta.
     *
     * @param jugador1 primer jugador, que comienza la primera baza
     * @param jugador2 segundo jugador
     * @param baraja   baraja completa (40 cartas) de la que se roba
     * @throws IllegalArgumentException si algún jugador o la baraja son nulos, si
     *                                  ambos jugadores son el mismo, si alguno
     *                                  tiene cartas en la mano o si la baraja no
     *                                  tiene sus 40 cartas
     */
    public Brisca(Jugador jugador1, Jugador jugador2, Baraja baraja) {
    }

    /** @return primer jugador */
    public Jugador getJugador1() {
        return null;
    }

    /** @return segundo jugador */
    public Jugador getJugador2() {
        return null;
    }

    /** @return carta de triunfo, la última que se entrega */
    public Carta getTriunfo() {
        return null;
    }

    /** @return palo de triunfo de la partida */
    public Palo getPaloTriunfo() {
        return null;
    }

    /** @return número de cartas que quedan por robar */
    public int getCartasRestantes() {
        return 0;
    }

    /** @return jugador al que le corresponde jugar */
    public Jugador getJugadorEnTurno() {
        return null;
    }
}