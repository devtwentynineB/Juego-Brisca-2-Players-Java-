package uva.tds;

import java.util.List;
import java.util.Map;

/**
 * Datos de una partida completa leídos de un fichero JSON: reparto inicial,
 * triunfo, orden del mazo, jugadas y resultados de cada baza, y resumen final.
 *
 * @param jugadorInicial jugador que comienza la primera baza
 * @param triunfo        carta de triunfo, en formato "valor-palo"
 * @param manos          cartas iniciales de cada jugador, por nombre
 * @param mazo           cartas del mazo en orden de robo, sin el triunfo
 * @param rondas         bazas de la partida, en orden
 * @param resumen        resumen final de la partida
 * @author bryaauc
 */
record DatosPartida(String jugadorInicial, String triunfo,
        Map<String, List<String>> manos, List<String> mazo,
        List<Ronda> rondas, Resumen resumen) {

    /**
     * Una carta asociada a un jugador: una jugada o una carta robada.
     *
     * @param jugador nombre del jugador
     * @param carta   carta en formato "valor-palo"
     */
    record Jugada(String jugador, String carta) {
    }

    /**
     * Una baza: estado al comenzar, jugadas y resultado esperado.
     *
     * @param numero            número de baza, desde 1
     * @param jugadorInicial    jugador que comienza la baza
     * @param manosIniciales    manos al comenzar la baza
     * @param cartasEnMazo      cartas del mazo sin contar el triunfo
     * @param triunfoDisponible true si el triunfo aún no se ha entregado
     * @param jugadas           cartas jugadas, en orden
     * @param ganador           ganador de la baza
     * @param cartasCapturadas  cartas capturadas, en orden de juego
     * @param puntosBaza        puntos de las dos cartas de la baza
     * @param roban             cartas robadas al terminar, en orden
     * @param manosResultantes  manos tras robar
     * @param puntosAcumulados  puntos acumulados de cada jugador
     */
    record Ronda(int numero, String jugadorInicial,
            Map<String, List<String>> manosIniciales, int cartasEnMazo,
            boolean triunfoDisponible, List<Jugada> jugadas, String ganador,
            List<String> cartasCapturadas, int puntosBaza, List<Jugada> roban,
            Map<String, List<String>> manosResultantes,
            Map<String, Integer> puntosAcumulados) {
    }

    /**
     * Resumen final de un jugador.
     *
     * @param bazasGanadas  bazas ganadas
     * @param cartasTotales cartas capturadas
     * @param totalPuntos   puntos totales
     */
    record ResumenJugador(int bazasGanadas, int cartasTotales, int totalPuntos) {
    }

    /**
     * Resumen final de la partida.
     *
     * @param totalRondas número de bazas disputadas
     * @param jugadores   resumen de cada jugador, por nombre
     * @param ganador     nombre del ganador; vacío o "empate" si hay empate
     */
    record Resumen(int totalRondas, Map<String, ResumenJugador> jugadores,
            String ganador) {

        /**
         * Indica si el resumen declara un empate.
         *
         * @return true si no hay ganador o este es "empate"
         */
        boolean esEmpate() {
            return ganador.isBlank() || ganador.equalsIgnoreCase("empate");
        }
    }
}