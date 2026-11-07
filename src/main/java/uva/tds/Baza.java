package uva.tds;

/**
 * Baza de la Brisca simplificada: las dos cartas jugadas, una por cada jugador,
 * en una ronda. Determina qué jugador la gana según el palo de triunfo y la
 * jerarquía de las cartas.
 *
 * <p>
 * Reglas para determinar el ganador:
 * <ul>
 * <li>Si solo una carta es del palo de triunfo, gana esa carta.</li>
 * <li>Si ambas son del mismo palo (triunfo o no), gana la de mayor jerarquía
 * (número de jerarquía menor).</li>
 * <li>Si son de palos distintos y ninguna es de triunfo, gana la carta jugada
 * en primer lugar.</li>
 * </ul>
 *
 * @author giarave
 */

public class Baza {
    private Jugador jugador1;
    private Carta carta1;
    private Jugador jugador2;
    private Carta carta2;
    private final Palo triunfo;

    /**
     * Crea una baza vacía para el palo de triunfo indicado.
     *
     * @param triunfo palo de triunfo de la partida
     * @throws IllegalArgumentException si el palo de triunfo es nulo
     */

    public Baza(Palo triunfo) {
        if (triunfo == null) {
            throw new IllegalArgumentException("El palo de triunfo no puede ser nulo");
        }
        this.triunfo = triunfo;
    }

    /**
     * Registra la carta jugada por un jugador en esta baza.
     *
     * @param jugador jugador que juega la carta
     * @param carta   carta jugada
     * @throws IllegalArgumentException si el jugador o la carta son nulos,
     *                                  o si el jugador ya ha jugado en esta baza
     * @throws IllegalStateException    si la baza ya tiene dos cartas
     */

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

    /**
     * Indica si ambos jugadores han jugado ya su carta.
     *
     * @return true si la baza tiene dos cartas jugadas; false en otro caso
     */

    public boolean estaCompleta() {
        return carta2 != null;
    }

    /**
     * Obtiene el jugador que gana la baza.
     *
     * @return jugador ganador de la baza
     * @throws IllegalStateException si la baza no está completa
     */

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
