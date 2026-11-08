package uva.tds;

import java.util.ArrayList;
import java.util.List;

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

    private final Jugador jugador1;
    private final Jugador jugador2;
    private Jugador jugadorEnTurno;
    private final Baraja baraja;
    private Baza bazaActual;
    private final List<Carta> cartasEnLaMesa = new ArrayList<>();

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
        this.bazaActual = nuevaBaza();
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

    private void finalizarBaza() {
        Jugador ganador = bazaActual.getGanador();
        for (Carta carta : cartasEnLaMesa) {
            ganador.capturar(carta);
        }
        cartasEnLaMesa.clear();
        bazaActual = nuevaBaza();
        robarCartas();
    }

    private Baza nuevaBaza() {
        return new Baza(baraja.getTriunfo().getPalo());
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

    private void robarCartas() {
        Jugador primero = jugadorEnTurno;
        robarSiQuedanCartas(primero);
        robarSiQuedanCartas(getOponente(primero));
    }

    private void robarSiQuedanCartas(Jugador jugador) {
        if (baraja.getCartasRestantes() > 0) {
            jugador.recibirCarta(baraja.robarCarta());
        }
    }
}
