package uva.tds;

import java.util.Locale;

/**
 * Conversión entre cartas y su representación en texto "valor-palo"
 * (por ejemplo "5-bastos"), el formato de los ficheros JSON.
 *
 * @author bryaauc
 */
final class CartaTest {

    private static final String SEPARADOR = "-";

    private CartaTest() {
    }

    /**
     * Obtiene el texto de una carta.
     *
     * @param carta carta a convertir
     * @return texto con el valor y el palo en minúsculas, como "5-bastos"
     */
    static String de(Carta carta) {
        return carta.getValor() + SEPARADOR + carta.getPalo().name().toLowerCase(Locale.ROOT);
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
        String[] partes = texto == null ? new String[0] : texto.split(SEPARADOR);
        if (partes.length != 2) {
            throw new IllegalArgumentException("Formato de carta no válido: " + texto);
        }
        return new Carta(Palo.valueOf(partes[1].toUpperCase(Locale.ROOT)),
                Integer.parseInt(partes[0]));
    }
}
