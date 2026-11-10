package uva.tds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import uva.tds.DatosPartida.Jugada;
import uva.tds.DatosPartida.Resumen;
import uva.tds.DatosPartida.ResumenJugador;
import uva.tds.DatosPartida.Ronda;

/**
 * Lector de ficheros JSON que describen una partida completa de Brisca.
 *
 * @author bryaauc
 */
final class PartidaJson {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private PartidaJson() {
    }

    /**
     * Lee un fichero JSON con la partida completa.
     *
     * @param ruta ruta del fichero
     * @return datos de la partida
     * @throws IllegalArgumentException si la ruta es nula, si el fichero no
     *                                  existe o no se puede leer, o si le faltan
     *                                  campos obligatorios
     */
    static DatosPartida leer(String ruta) {
        if (ruta == null) {
            throw new IllegalArgumentException("La ruta no puede ser nula");
        }
        try {
            return leerPartida(MAPPER.readTree(new File(ruta)).required("partida"));
        } catch (IOException e) {
            throw new IllegalArgumentException("No se puede leer el fichero: " + ruta, e);
        }
    }

    private static DatosPartida leerPartida(JsonNode partida) {
        JsonNode inicio = partida.required("inicio");
        List<Ronda> rondas = new ArrayList<>();
        for (JsonNode ronda : partida.required("rondas")) {
            rondas.add(leerRonda(ronda));
        }
        return new DatosPartida(
                inicio.required("jugador_inicial").asText(),
                inicio.required("triunfo").asText(),
                leerManos(inicio.required("manos")),
                leerTextos(inicio.required("mazo")),
                rondas,
                leerResumen(partida.required("resumen_partida")));
    }

    private static Ronda leerRonda(JsonNode ronda) {
        JsonNode inicio = ronda.required("inicio");
        JsonNode fin = ronda.required("final");
        return new Ronda(
                ronda.required("numero").asInt(),
                inicio.required("jugador_inicial").asText(),
                leerManos(inicio.required("manos")),
                inicio.required("cartas_en_mazo").asInt(),
                inicio.required("triunfo_disponible").asBoolean(),
                leerJugadas(ronda.required("jugadas"), "juega"),
                fin.required("ganador").asText(),
                leerTextos(fin.required("cartas_capturadas")),
                fin.required("puntos_baza").asInt(),
                leerJugadas(fin.required("roban"), "carta"),
                leerManos(fin.required("manos_resultantes")),
                leerPuntos(fin.required("puntos_acumulados")));
    }

    private static Resumen leerResumen(JsonNode resumen) {
        Map<String, ResumenJugador> jugadores = new LinkedHashMap<>();
        JsonNode porJugador = resumen.required("resumen_por_jugador");
        for (String nombre : nombres(porJugador)) {
            JsonNode jugador = porJugador.required(nombre);
            jugadores.put(nombre, new ResumenJugador(
                    jugador.required("bazas_ganadas").asInt(),
                    jugador.required("cartas_totales").asInt(),
                    jugador.required("total_puntos").asInt()));
        }
        JsonNode ganador = resumen.get("ganador");
        String nombreGanador = ganador == null || ganador.isNull() ? "" : ganador.asText();
        return new Resumen(resumen.required("total_rondas").asInt(), jugadores, nombreGanador);
    }

    private static List<Jugada> leerJugadas(JsonNode nodo, String campoCarta) {
        List<Jugada> jugadas = new ArrayList<>();
        for (JsonNode jugada : nodo) {
            jugadas.add(new Jugada(jugada.required("jugador").asText(),
                    jugada.required(campoCarta).asText()));
        }
        return jugadas;
    }

    private static List<String> leerTextos(JsonNode nodo) {
        List<String> textos = new ArrayList<>();
        for (JsonNode texto : nodo) {
            textos.add(texto.asText());
        }
        return textos;
    }

    private static Map<String, List<String>> leerManos(JsonNode nodo) {
        Map<String, List<String>> manos = new LinkedHashMap<>();
        for (String nombre : nombres(nodo)) {
            manos.put(nombre, leerTextos(nodo.required(nombre)));
        }
        return manos;
    }

    private static Map<String, Integer> leerPuntos(JsonNode nodo) {
        Map<String, Integer> puntos = new LinkedHashMap<>();
        for (String nombre : nombres(nodo)) {
            puntos.put(nombre, nodo.required(nombre).asInt());
        }
        return puntos;
    }

    private static Iterable<String> nombres(JsonNode objeto) {
        return objeto::fieldNames;
    }
}