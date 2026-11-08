package uva.tds;

import java.util.ArrayList;
import java.util.List;

/**
 * Partida de la Brisca simplificada para dos jugadores. Controla de quién es
 * el turno, valida las cartas que se juegan, entrega las cartas de cada baza a
 * su ganador y reparte las cartas de la baraja al finalizar cada baza.
 *
 * <p>
 * El jugador que comienza cada baza se alterna entre los dos jugadores,
 * independientemente de quién haya ganado la baza anterior. Comienza la
 * primera baza el primer jugador indicado al crear la partida. Al finalizar
 * una baza roba primero el jugador que va a comenzar la siguiente. La carta de
 * triunfo es la última carta de la baraja y, por tanto, la última en
 * entregarse.
 * </p>
 *
 * @author giarave
 */
public class Partida {

    private final Jugador jugador1;
    private final Jugador jugador2;
    private final Baraja baraja;
    private Jugador jugadorEnTurno;
    private Baza bazaActual;
    private final List<Carta> cartasEnLaMesa = new ArrayList<>();

    /**
     * Crea una partida entre dos jugadores con una baraja mezclada al azar.
     * Comienza el primer jugador.
     *
     * @param jugador1 primer jugador, que comienza la primera baza
     * @param jugador2 segundo jugador
     * @throws IllegalArgumentException si alguno de los jugadores es nulo o si
     *                                  ambos son el mismo jugador
     */
    public Partida(Jugador jugador1, Jugador jugador2) {
        this(jugador1, jugador2, new Baraja());
    }

    /**
     * Crea una partida entre dos jugadores con la baraja indicada. Comienza el
     * primer jugador. La carta de triunfo es la de la baraja.
     *
     * @param jugador1 primer jugador, que comienza la primera baza
     * @param jugador2 segundo jugador
     * @param baraja   baraja de la que se roban las cartas
     * @throws IllegalArgumentException si alguno de los jugadores o la baraja
     *                                  son nulos, o si ambos jugadores son el
     *                                  mismo
     */
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
        this.bazaActual = nuevaBaza();
    }

    /**
     * Obtiene el número de cartas que quedan por robar en la baraja.
     *
     * @return número de cartas restantes de la baraja
     */
    public int getCartasRestantes() {
        return baraja.getCartasRestantes();
    }

    /**
     * Obtiene la carta que determina el palo de triunfo de la partida.
     *
     * @return carta de triunfo
     */
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
     * Tras la primera carta de una baza el turno pasa al otro jugador. Al jugar
     * la segunda carta se completa la baza: el ganador captura las dos cartas,
     * ambos jugadores roban una carta (si quedan en la baraja), empezando por
     * quien comienza la siguiente baza, y esa baza la comienza el jugador que
     * jugó en segundo lugar, de modo que el inicio de baza se alterna.
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
        bazaActual.jugar(jugador, carta);
        cartasEnLaMesa.add(carta);
        if (bazaActual.estaCompleta()) {
            finalizarBaza();
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

    /** Entrega las cartas de la baza a su ganador, prepara la siguiente y roba. */
    private void finalizarBaza() {
        Jugador ganador = bazaActual.getGanador();
        for (Carta carta : cartasEnLaMesa) {
            ganador.capturar(carta);
        }
        cartasEnLaMesa.clear();
        bazaActual = nuevaBaza();
        robarCartas();
    }

    /** Crea una baza vacía con el palo de triunfo de la partida. */
    private Baza nuevaBaza() {
        return new Baza(baraja.getTriunfo().getPalo());
    }

    /**
     * Hace robar a ambos jugadores, empezando por quien comienza la siguiente baza.
     */
    private void robarCartas() {
        Jugador primero = jugadorEnTurno;
        robarSiQuedanCartas(primero);
        robarSiQuedanCartas(getOponente(primero));
    }

    /** Entrega una carta de la baraja al jugador si todavía quedan cartas. */
    private void robarSiQuedanCartas(Jugador jugador) {
        if (baraja.getCartasRestantes() > 0) {
            jugador.recibirCarta(baraja.robarCarta());
        }
    }

    /** Devuelve el jugador contrario al indicado. */
    private Jugador getOponente(Jugador jugador) {
        return jugador == jugador1 ? jugador2 : jugador1;
    }
}