package uva.tds;

/**
 * Conversión entre cartas y su representación en texto "valor-palo"
 * (por ejemplo "5-bastos"), el formato de los ficheros JSON.
 *
 * @author bryaauc
 */
final class CartaTexto {

    private CartaTexto() {
    }

    /**
     * Obtiene el texto de una carta.
     *
     * @param carta carta a convertir
     * @return texto con el valor y el palo en minúsculas, como "5-bastos"
     */
    static String de(Carta carta) {
        return "";
    }

    /**
     * Crea la carta que representa un texto "valor-palo".
     *
     * @param texto texto de la carta, como "5-bastos"
     * @return carta con ese valor y palo
     * @throws IllegalArgumentException si el formato no es "valor-palo", si el
     *                                  palo no existe o si el valor no es válido
     */
    static Carta aCarta(String texto) {
        return new Carta(Palo.OROS, 1);
    }
}