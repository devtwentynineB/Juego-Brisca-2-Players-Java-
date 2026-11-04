package uva.tds;

import java.util.List;

/**
 * Representa a un jugador identificado por su nombre.
 *
 * @author alvacun
 */
public class Jugador {
    private String nombre;

    /**
     * Crea un jugador con el nombre indicado.
     *
     * @param nombre nombre del jugador
     * @throws IllegalArgumentException si el nombre es nulo, vacío o contiene
     *                                  únicamente espacios
     */
    public Jugador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede ser nulo ni vacío");
        }

        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    public void recibirCarta(Carta carta) {
    }

    public Carta jugarCarta(Carta carta) {
        return null;
    }

    public List<Carta> getMano() {
        return null;
    }
}