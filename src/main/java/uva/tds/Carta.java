package uva.tds;

/**
 * Representa una carta de la baraja española de 40 cartas utilizada en la
 * versión simplificada de la Brisca.
 *
 * <p>
 * La baraja está formada por los palos oros, copas, espadas y bastos. Los
 * valores nominales permitidos son 1, 2, 3, 4, 5, 6, 7, 10, 11 y 12; no se
 * incluyen los ochos ni los nueves.
 * </p>
 *
 * @author alvacun
 */
public class Carta {
    private final Palo palo;
    private final int valor;

    /**
     * Crea una carta con el palo y el valor nominal indicados.
     *
     * @param palo  palo de la carta
     * @param valor valor nominal de la carta
     * @throws IllegalArgumentException si el valor es menor que 1, mayor que
     *                                  12, o igual a 8 o 9, valores que no forman
     *                                  parte de la baraja
     */
    public Carta(Palo palo, int valor) {
        if (valor < 1 || valor > 12 || valor == 8 || valor == 9) {
            throw new IllegalArgumentException(
                    "El valor de la carta debe estar entre 1 y 12, excepto 8 y 9: " + valor);
        }

        this.palo = palo;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public Palo getPalo() {
        return palo;
    }

    /**
     * Obtiene los puntos de la carta según las reglas de la Brisca. El as
     * vale 11 puntos, el tres 10, el rey 4, el caballo 3 y la sota 2; el
     * resto de cartas válidas no aporta puntos.
     *
     * @return los puntos asociados al valor de la carta
     */
    public int getPuntos() {
        return switch (valor) {
            case 1 -> 11;
            case 3 -> 10;
            case 12 -> 4;
            case 11 -> 3;
            case 10 -> 2;
            default -> 0;
        };
    }

    /**
     * Obtiene la jerarquía de la carta según las reglas de la Brisca. La
     * jerarquía 1 corresponde a la carta más fuerte y la 10 a la más débil.
     * El orden es as, tres, rey, caballo, sota, siete, seis, cinco, cuatro y
     * dos.
     *
     * @return la posición jerárquica de la carta
     */
    public int getJerarquia() {
        return switch (valor) {
            case 1 -> 1;
            case 3 -> 2;
            case 12 -> 3;
            case 11 -> 4;
            case 10 -> 5;
            case 7 -> 6;
            case 6 -> 7;
            case 5 -> 8;
            case 4 -> 9;
            case 2 -> 10;
            default -> 0;
        };
    }
}