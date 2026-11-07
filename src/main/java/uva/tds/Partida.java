package uva.tds;

public class Partida {

    private final Jugador jugador1;
    private final Jugador jugador2;
    private Jugador jugadorEnTurno;

    public Partida(Jugador jugador1, Jugador jugador2) {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalArgumentException("Los jugadores no pueden ser nulos");
        }
        if (jugador1 == jugador2) {
            throw new IllegalArgumentException("Los dos jugadores deben ser distintos");
        }
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorEnTurno = jugador1;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public Jugador getJugadorEnTurno() {
        return jugadorEnTurno;
    }
    public void JugarCarta(Jugador jugador, Carta carta) {
        if(jugador == null || carta == null) {
            throw new IllegalArgumentException("El jugador y la carta no pueden ser nulos");
        }
        if(jugador != jugador1 && jugador != jugador2) {
            throw new IllegalArgumentException("El jugador no pertenece a la partida");
        }
        if(jugador != getJugadorEnTurno()) {
            throw new IllegalStateException("No es el turno de este jugador");
        }
        if(!jugador.getMano().contains(carta)) {
            throw new IllegalArgumentException("La carta no esta en la mano del jugador");
        }
        jugador.jugarCarta(carta);
        jugadorEnTurno = (jugador == jugador1) ? jugador2 : jugador1;
    }
}
