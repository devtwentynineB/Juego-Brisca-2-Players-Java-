package uva.tds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.junit.jupiter.api.Nested;

class PartidaTest {

    private Jugador ana;
    private Jugador luis;
    private Jugador pedro;
    private Carta cartaAna;
    private Carta cartaLuis;
    private Partida partida;

    @BeforeEach
    void setUp() {
        ana = new Jugador("Ana");
        luis = new Jugador("Luis");
        pedro = new Jugador("Pedro");
        cartaAna = new Carta(Palo.COPAS, 1);
        cartaLuis = new Carta(Palo.ESPADAS, 3);
        ana.recibirCarta(cartaAna);
        luis.recibirCarta(cartaLuis);
        partida = new Partida(ana, luis);
    }

    @Nested
    class CreacionDeLaPartida {

        @Test
        void crearPartidaConPrimerJugadorNuloLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Partida(null, luis));
        }

        @Test
        void crearPartidaConSegundoJugadorNuloLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Partida(ana, null));
        }

        @Test
        void crearPartidaConElMismoJugadorDosVecesLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class, () -> new Partida(ana, ana));
        }

        @Test
        void crearPartidaValidaGuardaLosDosJugadores() {
            Partida partida = new Partida(ana, luis);
            assertEquals(ana, partida.getJugador1());
            assertEquals(luis, partida.getJugador2());
        }

        @Test
        void enUnaPartidaNuevaComienzaElPrimerJugador() {
            Partida partida = new Partida(ana, luis);
            assertEquals(ana, partida.getJugadorEnTurno());
        }
    }

    @Nested
    class JUgadasNoValidas {

        @Test
        void jugarCartaConJugadorNuloLanzaExcepcion() {
            Carta carta = new Carta(Palo.COPAS, 1);
            assertThrows(IllegalArgumentException.class,
                    () -> partida.jugarCarta(null, carta));
        }

        @Test
        void jugarCartaConCartaNulaLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class,
                    () -> partida.jugarCarta(ana, null));
        }

        @Test
        void jugarCartaDeUnJugadorAjenoALaPartidaLanzaExcepcion() {
            Carta carta = new Carta(Palo.COPAS, 1);
            pedro.recibirCarta(carta);
            assertThrows(IllegalArgumentException.class,
                    () -> partida.jugarCarta(pedro, carta));
        }

        @Test
        void jugarFueraDeTurnoLanzaExcepcion() {
            assertThrows(IllegalStateException.class,
                    () -> partida.jugarCarta(luis, cartaLuis));
        }

        @Test
        void jugarFueraDeTurnoNoModificaLaManoDelJugador() {
            assertThrows(IllegalStateException.class,
                    () -> partida.jugarCarta(luis, cartaLuis));
            assertEquals(List.of(cartaLuis), luis.getMano());
        }

        @Test
        void jugarUnaCartaQueNoEstaEnLaManoLanzaExcepcion() {
            assertThrows(IllegalArgumentException.class,
                    () -> partida.jugarCarta(ana, cartaLuis));
        }

        @Test
        void jugarUnaCartaQueNoEstaEnLaManoNoModificaLaMano() {
            assertThrows(IllegalArgumentException.class,
                    () -> partida.jugarCarta(ana, cartaLuis));
            assertEquals(List.of(cartaAna), ana.getMano());
        }
    }

    @Nested
    class JugadaValida {

        @Test
        void jugarUnaCartaValidaLaQuitaDeLaManoDelJugador() {
            partida.jugarCarta(ana, cartaAna);
            assertEquals(List.of(), ana.getMano());
        }

        @Test
        void trasJugarElPrimerJugadorElTurnoEsDelOtro() {
            partida.jugarCarta(ana, cartaAna);
            assertEquals(luis, partida.getJugadorEnTurno());
        }

        @Test
        void elSegundoJugadorPuedeJugarTrasElPrimero() {
            partida.jugarCarta(ana, cartaAna);
            assertDoesNotThrow(() -> partida.jugarCarta(luis, cartaLuis));
        }

        @Test
        void elMismoJugadorNoPuedeJugarDosVecesSeguidas() {
            Carta otraCarta = new Carta(Palo.BASTOS, 4);
            ana.recibirCarta(otraCarta);
            partida.jugarCarta(ana, cartaAna);
            assertThrows(IllegalStateException.class,
                    () -> partida.jugarCarta(ana, otraCarta));
        }

    }

    @Nested
    class AlternanciaDeBazas {

        @Test
        void alCompletarseLaBazaComienzaLaSiguienteElJugadorQueJugoSegundo() {
            partida.jugarCarta(ana, cartaAna);
            partida.jugarCarta(luis, cartaLuis);
            assertEquals(luis, partida.getJugadorEnTurno());
        }

        @Test
        void elJugadorQueComienzaLaBazaSeAlternaEnBazasSucesivas() {
            Carta segundaAna = new Carta(Palo.BASTOS, 2);
            Carta segundaLuis = new Carta(Palo.COPAS, 5);
            ana.recibirCarta(segundaAna);
            luis.recibirCarta(segundaLuis);

            partida.jugarCarta(ana, cartaAna);
            partida.jugarCarta(luis, cartaLuis);

            assertEquals(luis, partida.getJugadorEnTurno());
            partida.jugarCarta(luis, segundaLuis);
            assertEquals(ana, partida.getJugadorEnTurno());
            partida.jugarCarta(ana, segundaAna);

            assertEquals(ana, partida.getJugadorEnTurno());
        }
    }
}
