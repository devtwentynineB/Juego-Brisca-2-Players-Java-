package uva.tds;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un jugador identificado por su nombre.
 *
 * @author alvacun
 */
public class Jugador {
    private String nombre;
    private List<Carta> mano;

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
        this.mano = new ArrayList<>();
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
        mano.add(carta);
    }

    public Carta jugarCarta(Carta carta) {
        if (!mano.contains(carta)) {
            throw new IllegalArgumentException("La carta no está en la mano del jugador");
        }

        mano.remove(carta);
        return carta;
    }

    public List<Carta> getMano() {
        return mano;
    }
}