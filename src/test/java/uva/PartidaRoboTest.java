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
 * Tests del robo de cartas al finalizar una baza.
 *
 * @author giarave
 */
class PartidaRoboTest {

    private Jugador ana;
    private Jugador luis;
    private List<Carta> cartas;
    private Partida partida;

    @BeforeEach
    void setUp() {
        ana = new Jugador("Ana");
        luis = new Jugador("Luis");
        cartas = BarajaFixture.cuarentaCartasConTriunfo(Palo.OROS, 7);
        Baraja baraja = new Baraja(cartas);
        // Reparto: Ana recibe cartas 0, 1 y 2; Luis recibe 3, 4 y 5
        for (int i = 0; i < 3; i++) {
            ana.recibirCarta(baraja.robarCarta());
        }
        for (int i = 0; i < 3; i++) {
            luis.recibirCarta(baraja.robarCarta());
        }
        partida = new Partida(ana, luis, baraja);
    }

    private void jugarBaza(Jugador primero, Jugador segundo) {
        partida.jugarCarta(primero, primero.getMano().get(0));
        partida.jugarCarta(segundo, segundo.getMano().get(0));
    }

    @Test
    void alFinalizarLaBazaRobaPrimeroQuienComienzaLaSiguiente() {
        jugarBaza(ana, luis);
        // Luis jugó segundo, así que roba primero la carta 6; Ana roba la 7
        assertEquals(List.of(cartas.get(4), cartas.get(5), cartas.get(6)), luis.getMano());
        assertEquals(List.of(cartas.get(1), cartas.get(2), cartas.get(7)), ana.getMano());
    }

    @Test
    void alFinalizarLaBazaSeRoban2CartasDeLaBaraja() {
        jugarBaza(ana, luis);
        assertEquals(32, partida.getCartasRestantes());
    }

    @Test
    void conUnaSolaCartaJugadaNadieRoba() {
        partida.jugarCarta(ana, ana.getMano().get(0));
        assertEquals(2, ana.getMano().size());
        assertEquals(3, luis.getMano().size());
        assertEquals(34, partida.getCartasRestantes());
    }

    @Test
    void elOrdenDeRoboSeAlternaEnBazasSucesivas() {
        jugarBaza(ana, luis);
        jugarBaza(luis, ana);
        // Baza 2: Ana jugó segunda, así que roba primero la carta 8; Luis roba la 9
        assertEquals(List.of(cartas.get(2), cartas.get(7), cartas.get(8)), ana.getMano());
        assertEquals(List.of(cartas.get(5), cartas.get(6), cartas.get(9)), luis.getMano());
        assertEquals(30, partida.getCartasRestantes());
    }
}