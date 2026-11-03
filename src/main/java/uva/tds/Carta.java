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
 * @author Todos los integrantes del grupo
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