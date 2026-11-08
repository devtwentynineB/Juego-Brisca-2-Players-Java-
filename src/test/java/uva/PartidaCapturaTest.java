package uva;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uva.tds.Baraja;
import uva.tds.Carta;
import uva.tds.Jugador;
import uva.tds.Palo;
import uva.tds.Partida;

/**
 * Tests de la captura de cartas al finalizar una baza.
 *
 * @author giarave
 */
class PartidaCapturaTest {

    private Jugador ana;
    private Jugador luis;
    private Carta asCopas;
    private Carta dosCopas;
    private Carta tresCopas;
    private Carta dosOros;
    private Carta reyBastos;
    private Carta cuatroEspadas;
    private Partida partida;

    @BeforeEach
    void setUp() {
        ana = new Jugador("Ana");
        luis = new Jugador("Luis");
        asCopas = new Carta(Palo.COPAS, 1);
        dosCopas = new Carta(Palo.COPAS, 2);
        tresCopas = new Carta(Palo.COPAS, 3);
        dosOros = new Carta(Palo.OROS, 2);
        reyBastos = new Carta(Palo.BASTOS, 12);
        cuatroEspadas = new Carta(Palo.ESPADAS, 4);
        // Triunfo: oros
        Baraja baraja = new Baraja(BarajaFixture.cuarentaCartasConTriunfo(Palo.OROS, 7));
        partida = new Partida(ana, luis, baraja);
    }

    @Test
    void ganaLaPrimeraCartaYSeCapturanLasDosEnOrdenDeJuego() {
        ana.recibirCarta(asCopas);
        luis.recibirCarta(dosCopas);
        partida.jugarCarta(ana, asCopas);
        partida.jugarCarta(luis, dosCopas);
        assertEquals(List.of(asCopas, dosCopas), ana.getCartasCapturadas());
    }

    @Test
    void ganaLaSegundaCartaYSeCapturanLasDosEnOrdenDeJuego() {
        ana.recibirCarta(tresCopas);
        luis.recibirCarta(asCopas);
        partida.jugarCarta(ana, tresCopas);
        partida.jugarCarta(luis, asCopas);
        assertEquals(List.of(tresCopas, asCopas), luis.getCartasCapturadas());
    }

    @Test
    void ganaLaCartaDeTriunfoAunqueSeaMasDebil() {
        ana.recibirCarta(asCopas);
        luis.recibirCarta(dosOros);
        partida.jugarCarta(ana, asCopas);
        partida.jugarCarta(luis, dosOros);
        assertEquals(List.of(asCopas, dosOros), luis.getCartasCapturadas());
    }

    @Test
    void elPerdedorDeLaBazaNoCapturaCartas() {
        ana.recibirCarta(asCopas);
        luis.recibirCarta(dosCopas);
        partida.jugarCarta(ana, asCopas);
        partida.jugarCarta(luis, dosCopas);
        assertEquals(List.of(), luis.getCartasCapturadas());
    }

    @Test
    void conUnaSolaCartaJugadaNadieCaptura() {
        ana.recibirCarta(asCopas);
        partida.jugarCarta(ana, asCopas);
        assertEquals(List.of(), ana.getCartasCapturadas());
        assertEquals(List.of(), luis.getCartasCapturadas());
    }

    @Test
    void lasCapturasSeAcumulanEnBazasSucesivas() {
        ana.recibirCarta(asCopas);
        ana.recibirCarta(reyBastos);
        luis.recibirCarta(dosCopas);
        luis.recibirCarta(cuatroEspadas);

        // Baza 1: comienza Ana y gana Ana (as de copas)
        partida.jugarCarta(ana, asCopas);
        partida.jugarCarta(luis, dosCopas);

        // Baza 2: comienza Luis; palos distintos sin triunfo, gana el primero en jugar
        partida.jugarCarta(luis, cuatroEspadas);
        partida.jugarCarta(ana, reyBastos);

        assertEquals(List.of(asCopas, dosCopas), ana.getCartasCapturadas());
        assertEquals(List.of(cuatroEspadas, reyBastos), luis.getCartasCapturadas());
    }
}
