package uva.tds;

import java.util.ArrayList;
import java.util.List;

/**
 * Fixture compartida por los tests de Brisca: baraja de 40 cartas en orden
 * conocido (oros, copas, espadas y bastos; 1-7, 10, 11, 12 en cada palo).
 * La última carta, y por tanto el triunfo, es el rey de bastos.
 *
 * @author bryaauc
 */
final class BriscaFixture {

    private static final int[] VALORES = { 1, 2, 3, 4, 5, 6, 7, 10, 11, 12 };

    private BriscaFixture() {
    }

    static List<Carta> cuarentaCartasOrdenadas() {
        List<Carta> cartas = new ArrayList<>();
        for (Palo palo : Palo.values()) {
            for (int valor : VALORES) {
                cartas.add(new Carta(palo, valor));
            }
        }
        return cartas;
    }
}