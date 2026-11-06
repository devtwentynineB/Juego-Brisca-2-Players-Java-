package uva;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uva.tds.Baza;
import uva.tds.Carta;
import uva.tds.Jugador;
import uva.tds.Palo;

public class BazaTest {
    private Baza baza;
    private Jugador ana;

    @BeforeEach
    void setUp() {
        baza = new Baza(Palo.OROS);
        ana = new Jugador("Ana");
    }

    @Test
    void crearBazaConTriunfoNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Baza(null);
        });
    }

    @Test
    void jugarConJugadorNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            baza.jugar(null, new Carta(Palo.OROS, 1));
        });
    }

    @Test
    void jugarConCartaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            baza.jugar(ana, null);
        });
    }
}