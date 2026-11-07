package uva.tds;

/**
 * @author giarave
 */

public class Baza {
    private Jugador jugador1;
    private Carta carta1;
    private Jugador jugador2;
    private Carta carta2;
    private final Palo triunfo;

    public Baza(Palo triunfo) {
        if (triunfo == null) {
            throw new IllegalArgumentException("El palo de triunfo no puede ser nulo");
        }
        this.triunfo = triunfo;
    }

    public void jugar(Jugador jugador, Carta carta) {
        if (jugador == null || carta == null) {
            throw new IllegalArgumentException("Jugador y carta no pueden ser nulos");
        }
        if (estaCompleta()) {
            throw new IllegalStateException("La baza ya tiene dos cartas");
        }
        if (jugador1 == null) {
            jugador1 = jugador;
            carta1 = carta;
        } else {
            if (jugador == jugador1) {
                throw new IllegalArgumentException("El jugador ya ha jugado en esta baza");
            }
            jugador2 = jugador;
            carta2 = carta;
        }
    }

    public boolean estaCompleta() {
        return carta2 != null;
    }

    public Jugador getGanador() {
        if (!estaCompleta()) {
            throw new IllegalStateException("La baza no esta completa");
        }
        return ganaLaSegundaCarta() ? jugador2 : jugador1;
    }

    private boolean ganaLaSegundaCarta() {
        boolean primeraTriunfo = esTriunfo(carta1);
        boolean segundaTriunfo = esTriunfo(carta2);
        if (primeraTriunfo != segundaTriunfo) {
            return segundaTriunfo;
        }
        if (carta1.getPalo() == carta2.getPalo()) {
            return carta2.getJerarquia() < carta1.getJerarquia();
        }
        return false;
    }

    private boolean esTriunfo(Carta carta) {
        return carta.getPalo() == triunfo;
    }
}
