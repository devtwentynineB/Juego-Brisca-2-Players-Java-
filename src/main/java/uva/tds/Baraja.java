package uva.tds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja {
    private List<Carta> cartas;
    private Carta triunfo;

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

    public int getCartasRestantes() {
        return cartas.size();
    }

    public Carta getTriunfo() {
        return triunfo;
    }
}
