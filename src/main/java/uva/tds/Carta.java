package uva.tds;

public class Carta {
    private final Palo palo;
    private final int valor;

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
}