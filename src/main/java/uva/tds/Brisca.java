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

    private static final int CARTAS_INICIALES = 3;
    private static final int CARTAS_BARAJA = 40;

    private final Partida partida;

    public Brisca(Jugador jugador1, Jugador jugador2) {
        this(jugador1, jugador2, new Baraja());
    }

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

    public Jugador getJugador1() { return partida.getJugador1(); }
    public Jugador getJugador2() { return partida.getJugador2(); }
    public Carta getTriunfo() { return partida.getTriunfo(); }
    public Palo getPaloTriunfo() { return partida.getTriunfo().getPalo(); }
    public int getCartasRestantes() { return partida.getCartasRestantes(); }
    public Jugador getJugadorEnTurno() { return partida.getJugadorEnTurno(); }
}