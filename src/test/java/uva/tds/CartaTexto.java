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
        if (carta == null) {
            throw new IllegalArgumentException("La carta no puede ser nula");
        }
        return carta.getValor() + "-" + carta.getPalo().name().toLowerCase();
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
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto de la carta no puede estar vacío");
        }

        String[] partes = texto.split("-", 2);
        if (partes.length != 2 || partes[0].isBlank() || partes[1].isBlank()) {
            throw new IllegalArgumentException("El texto debe tener formato valor-palo");
        }

        int valor;
        try {
            valor = Integer.parseInt(partes[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El valor debe ser numérico", e);
        }

        Palo palo;
        try {
            palo = Palo.valueOf(partes[1].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El palo no existe", e);
        }

        return new Carta(palo, valor);
    }
}