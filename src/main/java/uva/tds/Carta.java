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
        return 0;
    }

    public int getJerarquia() {
        return 0;
    }
}