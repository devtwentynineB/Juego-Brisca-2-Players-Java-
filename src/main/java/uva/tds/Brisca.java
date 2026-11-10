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
 * @author bryaauc
 */
public class Brisca {

    private static final int CARTAS_INICIALES = 3;
    private static final int CARTAS_BARAJA = 40;

    private final Partida partida;
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
        this(jugador1, jugador2, new Baraja());
    }

    /**
     * Crea una partida con la baraja indicada y reparte las cartas iniciales:
     * 3 al primer jugador y después 3 al segundo. Si la creación falla no se
     * reparte ninguna carta.
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
        this.partida = new Partida(jugador1, jugador2, baraja);
        validarEstadoInicial(jugador1, jugador2, baraja);
        repartir(jugador1, baraja);
        repartir(jugador2, baraja);
    }

    private void validarEstadoInicial(Jugador jugador1, Jugador jugador2, Baraja baraja) {
        if (!jugador1.getMano().isEmpty() || !jugador2.getMano().isEmpty()) {
            throw new IllegalArgumentException("Los jugadores no pueden tener cartas al empezar");
        }
        if (baraja.getCartasRestantes() != CARTAS_BARAJA) {
            throw new IllegalArgumentException("La baraja debe estar completa");
        }
    }

    private void repartir(Jugador jugador, Baraja baraja) {
        for (int i = 0; i < CARTAS_INICIALES; i++) {
            jugador.recibirCarta(baraja.robarCarta());
        }
    }
    /**
     * Obtiene el primer jugador de la partida.
     *
     * @return primer jugador
     */
    public Jugador getJugador1() {
        return partida.getJugador1();
    }

    /**
     * Obtiene el segundo jugador de la partida.
     *
     * @return segundo jugador
     */
    public Jugador getJugador2() {
        return partida.getJugador2();
    }

    /**
     * Obtiene la carta de triunfo, que es la última carta de la baraja y la
     * última en entregarse.
     *
     * @return carta de triunfo
     */
    public Carta getTriunfo() {
        return partida.getTriunfo();
    }

    /**
     * Obtiene el palo de triunfo de la partida, que es el de la carta de triunfo.
     *
     * @return palo de triunfo
     */
    public Palo getPaloTriunfo() {
        return partida.getTriunfo().getPalo();
    }

    /**
     * Obtiene el número de cartas que quedan por robar en la baraja, incluida
     * la carta de triunfo mientras no se haya entregado.
     *
     * @return número de cartas restantes
     */
    public int getCartasRestantes() {
        return partida.getCartasRestantes();
    }

    /**
     * Obtiene el jugador al que le corresponde jugar una carta.
     *
     * @return jugador en turno
     */
    public Jugador getJugadorEnTurno() {
        return partida.getJugadorEnTurno();
    }
}