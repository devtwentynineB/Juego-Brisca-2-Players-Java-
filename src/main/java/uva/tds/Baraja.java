package uva.tds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una baraja española de 40 cartas para la versión simplificada
 * de la Brisca.
 *
 * <p>
 * La baraja contiene una carta de cada combinación de los cuatro palos y los
 * valores nominales 1, 2, 3, 4, 5, 6, 7, 10, 11 y 12. Al crear la baraja, sus
 * cartas se mezclan y se selecciona una de ellas como carta de triunfo.
 * </p>
 *
 * @author alvacun
 */
public class Baraja {
    /** Cartas que forman la baraja. */
    private List<Carta> cartas;

    /** Carta seleccionada como triunfo de la partida. */
    private Carta triunfo;
    private static final int NUMERO_CARTAS = 40;

    /**
     * Crea una baraja española completa, la mezcla y selecciona su carta de
     * triunfo.
     */
    public Baraja() {
        cartas = new ArrayList<>();

        for (Palo palo : Palo.values()) {
            for (int valor = 1; valor <= 7; valor++) {
                cartas.add(new Carta(palo, valor));
            }
            for (int valor = 10; valor <= 12; valor++) {
                cartas.add(new Carta(palo, valor));
            }
        }

        Collections.shuffle(cartas);
        triunfo = cartas.get(cartas.size() - 1);
    }

    public Baraja(List<Carta> cartas) {
        if (cartas == null) {
            throw new IllegalArgumentException("La lista de cartas no puede ser nula");
        }
        if (cartas.size() != NUMERO_CARTAS) {
            throw new IllegalArgumentException("La baraja debe tener 40 cartas");
        }
        for (Carta carta : cartas) {
            if (carta == null) {
                throw new IllegalArgumentException("La baraja no puede contener cartas nulas");
            }
        }
        this.cartas = new ArrayList<>(cartas);
    }

    /**
     * Obtiene el número de cartas que contiene actualmente la baraja.
     *
     * @return número de cartas restantes
     */
    public int getCartasRestantes() {
        return cartas.size();
    }

    /**
     * Obtiene la carta seleccionada como triunfo al crear la baraja.
     *
     * @return carta de triunfo
     */
    public Carta getTriunfo() {
        return triunfo;
    }

    /**
     * Extrae y devuelve la primera carta disponible de la baraja.
     *
     * @return carta extraída de la baraja
     * @throws IllegalStateException si el mazo está vacío
     */
    public Carta robarCarta() {
        if (cartas.isEmpty()) {
            throw new IllegalStateException("No quedan cartas en la baraja");
        }

        return cartas.remove(0);
    }
}
