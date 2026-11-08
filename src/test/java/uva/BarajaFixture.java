package uva;

import java.util.ArrayList;
import java.util.List;

import uva.tds.Carta;
import uva.tds.Palo;

/**
 * Fixture compartida por los tests: construye las 40 cartas de la baraja en un
 * orden conocido.
 *
 * @author giarave
 */
final class BarajaFixture {

    private static final int[] VALORES = { 1, 2, 3, 4, 5, 6, 7, 10, 11, 12 };

    private BarajaFixture() {
    }

    /**
     * Crea las 40 cartas ordenadas por palo y, dentro de cada palo, por valor.
     *
     * @return lista modificable con las 40 cartas
     */
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
