package uva;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uva.tds.Baza;
import uva.tds.Carta;
import uva.tds.Jugador;
import uva.tds.Palo;

public class BazaTest {
    private Baza baza;
    private Jugador ana;
    private Jugador luis;

    @BeforeEach
    void setUp() {
        baza = new Baza(Palo.OROS);
        ana = new Jugador("Ana");
        luis = new Jugador("Luis");
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
            baza.jugar(null, new Carta(Palo.COPAS, 1));
        });
    }

    @Test
    void jugarConCartaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            baza.jugar(ana, null);
        });
    }

    @Test
    void bazaRecienCreadoNoEstaCompleta() {
        assertFalse(baza.estaCompleta());
    }

    @Test
    void bazaConUnaCartaNoEstaCompleta() {
        baza.jugar(ana, new Carta(Palo.COPAS, 1));
        assertFalse(baza.estaCompleta());
    }

    @Test
    void bazaConDosCartasEstaCompleta() {
        baza.jugar(ana, new Carta(Palo.COPAS, 1));
        baza.jugar(luis, new Carta(Palo.COPAS, 2));
        assertTrue(baza.estaCompleta());
    }

    @Test
    void jugarUnaTercerCartaLanzaExcepcion() {
        baza.jugar(ana, new Carta(Palo.COPAS, 1));
        baza.jugar(luis, new Carta(Palo.COPAS, 2));
        assertThrows(IllegalStateException.class, () -> {
            baza.jugar(ana, new Carta(Palo.COPAS, 3));
        });
    }

    @Test
    void jugarDosVecesELMismoJugadorLanzaExcepcion() {
        baza.jugar(ana, new Carta(Palo.COPAS, 1));
        assertThrows(IllegalArgumentException.class, () -> {
            baza.jugar(ana, new Carta(Palo.COPAS, 2));
        });
    }
}