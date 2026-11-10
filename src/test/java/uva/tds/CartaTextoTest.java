package uva.tds;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests de la conversión de cartas a texto y viceversa.
 *
 * @author bryaauc
 */
class CartaTextoTest {

    @Test
    void laCartaSeConvierteEnValorYPaloEnMinusculas() {
        assertEquals("5-bastos", CartaTexto.de(new Carta(Palo.BASTOS, 5)));
    }

    @Test
    void elTextoSeConvierteEnCartaConSuValorYPalo() {
        Carta carta = CartaTexto.aCarta("12-copas");
        assertAll(
                () -> assertEquals(12, carta.getValor()),
                () -> assertEquals(Palo.COPAS, carta.getPalo()));
    }

    @Test
    void textoSinSeparadorLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> CartaTexto.aCarta("bastos"));
    }

    @Test
    void textoConPaloInexistenteLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> CartaTexto.aCarta("5-hojas"));
    }

    @Test
    void textoConValorNoNumericoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> CartaTexto.aCarta("as-oros"));
    }

    @Test
    void textoConValorQueNoEstaEnLaBarajaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> CartaTexto.aCarta("8-oros"));
    }
}