package uva.tds;

/**
 * Partida de la Brisca simplificada para dos jugadores. Controla de quién es
 * el turno y valida las cartas que se juegan.
 *
 * <p>
 * El jugador que comienza cada baza se alterna entre los dos jugadores,
 * independientemente de quién haya ganado la baza anterior. Comienza la
 * primera baza el primer jugador indicado al crear la partida.
 * </p>
 *
 * @author giarave
 */

public class Partida {

    private static final int CARTAS_POR_BAZA = 2;

    private final Jugador jugador1;
    private final Jugador jugador2;
    private Jugador jugadorEnTurno;
    private int cartasJugadasEnBaza;
    private final Baraja baraja;

    /**
     * Crea una partida entre dos jugadores. Comienza el primer jugador.
     *
     * @param jugador1 primer jugador, que comienza la primera baza
     * @param jugador2 segundo jugador
     * @throws IllegalArgumentException si alguno de los jugadores es nulo o si
     *                                  ambos son el mismo jugador
     */

    public Partida(Jugador jugador1, Jugador jugador2) {
        this(jugador1, jugador2, new Baraja());
    }

    public Partida(Jugador jugador1, Jugador jugador2, Baraja baraja) {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalArgumentException("Los jugadores no pueden ser nulos");
        }
        if (jugador1 == jugador2) {
            throw new IllegalArgumentException("Los dos jugadores deben ser distintos");
        }
        if (baraja == null) {
            throw new IllegalArgumentException("La baraja no puede ser nula");
        }
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.baraja = baraja;
        this.jugadorEnTurno = jugador1;
    }

    public int getCartasRestantes() {
        return baraja.getCartasRestantes();
    }

    public Carta getTriunfo() {
        return baraja.getTriunfo();
    }

    /**
     * Obtiene el primer jugador de la partida.
     *
     * @return primer jugador
     */

    public Jugador getJugador1() {
        return jugador1;
    }

    /**
     * Obtiene el segundo jugador de la partida.
     *
     * @return segundo jugador
     */

    public Jugador getJugador2() {
        return jugador2;
    }

    /**
     * Obtiene el jugador al que le corresponde jugar una carta.
     *
     * @return jugador en turno
     */

    public Jugador getJugadorEnTurno() {
        return jugadorEnTurno;
    }

    /**
     * Juega una carta de la mano de un jugador. La carta se elimina de su mano.
     * Tras la primera carta de una baza el turno pasa al otro jugador; al jugar
     * la segunda carta se completa la baza y la siguiente la comienza el jugador
     * que jugó en segundo lugar, de modo que el inicio de baza se alterna.
     *
     * @param jugador jugador que juega la carta
     * @param carta   carta que se juega
     * @throws IllegalArgumentException si el jugador o la carta son nulos, si el
     *                                  jugador no pertenece a la partida o si la
     *                                  carta no está en su mano
     * @throws IllegalStateException    si no es el turno del jugador
     */

    public void jugarCarta(Jugador jugador, Carta carta) {
        validarJugada(jugador, carta);
        jugador.jugarCarta(carta);
        cartasJugadasEnBaza++;
        if (cartasJugadasEnBaza == CARTAS_POR_BAZA) {
            cartasJugadasEnBaza = 0;
        } else {
            jugadorEnTurno = getOponente(jugador);
        }
    }

    /** Comprueba que la jugada es válida sin modificar el estado de la partida. */

    private void validarJugada(Jugador jugador, Carta carta) {
        if (jugador == null || carta == null) {
            throw new IllegalArgumentException("El jugador y la carta no pueden ser nulos");
        }
        if (jugador != jugador1 && jugador != jugador2) {
            throw new IllegalArgumentException("El jugador no pertenece a la partida");
        }
        if (jugador != jugadorEnTurno) {
            throw new IllegalStateException("No es el turno de este jugador");
        }
        if (!jugador.getMano().contains(carta)) {
            throw new IllegalArgumentException("La carta no esta en la mano del jugador");
        }
    }

    /** Devuelve el jugador contrario al indicado. */

    private Jugador getOponente(Jugador jugador) {
        return jugador == jugador1 ? jugador2 : jugador1;
    }
}
