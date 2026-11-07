package uva;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void getGanadorSinCartasJugadasLanzaExcepcion() {
        assertThrows(IllegalStateException.class, () -> {
            baza.getGanador();
        });
    }

    @Test
    void getGanadorConUnaSolaCartaLanzaExcepcion() {
        baza.jugar(ana, new Carta(Palo.COPAS, 1));
        assertThrows(IllegalStateException.class, () -> {
            baza.getGanador();
        });
    }

    @Test
    void soloLaSegundacartaEsTriunfoGanaLaSegunda() {
        baza.jugar(ana, new Carta(Palo.COPAS, 1));
        baza.jugar(luis, new Carta(Palo.OROS, 2));
        assertEquals(luis, baza.getGanador());
    }

    @Test
    void soloLaPrimeraCartaTriunfoGanaLaPrimera() {
        baza.jugar(ana, new Carta(Palo.OROS, 2));
        baza.jugar(luis, new Carta(Palo.COPAS, 1));
        assertEquals(ana, baza.getGanador());
    }

    @Test
    void mismoPaloSinTriunfoGanaLaSegundaSiEsMasFuerte() {
        baza.jugar(ana, new Carta(Palo.COPAS, 3));
        baza.jugar(luis, new Carta(Palo.COPAS, 1));
        assertEquals(luis, baza.getGanador());
    }

    @Test
    void mismoPaloSinTriunfoGanaLaPrimeraSiEsMasFuerte() {
        baza.jugar(ana, new Carta(Palo.COPAS, 1));
        baza.jugar(luis, new Carta(Palo.COPAS, 3));
        assertEquals(ana, baza.getGanador());
    }

    @Test
    void ambasTriunfoGanaLaSegundaSiEsMasFuerte() {
        baza.jugar(ana, new Carta(Palo.OROS, 7));
        baza.jugar(luis, new Carta(Palo.OROS, 1));
        assertEquals(luis, baza.getGanador());
    }

    @Test
    void ambasTriunfoGanaLaPrimeraSiEsMasFuerte() {
        baza.jugar(ana, new Carta(Palo.OROS, 1));
        baza.jugar(luis, new Carta(Palo.OROS, 7));
        assertEquals(ana, baza.getGanador());
    }

    @Test
    void cuatroGanaADosDelMismoPalo() {
        baza.jugar(ana, new Carta(Palo.COPAS, 2));
        baza.jugar(luis, new Carta(Palo.COPAS, 4));
        assertEquals(luis, baza.getGanador());
    }

    @Test
    void sieteGanaASeisDelMismoPalo() {
        baza.jugar(ana, new Carta(Palo.COPAS, 6));
        baza.jugar(luis, new Carta(Palo.COPAS, 7));
        assertEquals(luis, baza.getGanador());
    }

    @Test
    void distintoPaloSinTriunfoGanaLaPrimeraAunqueLaSegundaSeaMasFuerte() {
        baza.jugar(ana, new Carta(Palo.COPAS, 2)); // dois de copas, o mais fraco
        baza.jugar(luis, new Carta(Palo.ESPADAS, 1)); // as de espadas, o mais forte
        assertEquals(ana, baza.getGanador());
    }

    @Test
    void distintoPaloSinTriunfoGanaLaPrimeraSiEsMasFuerte() {
        baza.jugar(ana, new Carta(Palo.COPAS, 1));
        baza.jugar(luis, new Carta(Palo.ESPADAS, 2));
        assertEquals(ana, baza.getGanador());
    }

    @Test
    void distintoPaloSinTriunfoGanaElQueJuegaPrimeroSiSeInvierteElOrden() {
        baza.jugar(luis, new Carta(Palo.BASTOS, 1));
        baza.jugar(ana, new Carta(Palo.COPAS, 3));
        assertEquals(luis, baza.getGanador());
    }
}