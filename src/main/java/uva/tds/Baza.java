package uva.tds;

/**
 * @author giarave
 */

public class Baza {
    public Baza(Palo triunfo) {
        if(triunfo == null) {
            throw new IllegalArgumentException("El palo de triunfo no puede ser nulo");
        }
    }

    public void jugar(Jugador jugador, Carta carta) {
    }
}
