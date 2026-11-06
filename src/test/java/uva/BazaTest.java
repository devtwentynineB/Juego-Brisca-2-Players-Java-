package uva;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import uva.tds.Baza;

public class BazaTest {
    @Test 
    void crearBazaConTriunfoNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Baza(null);
        });
    }
}