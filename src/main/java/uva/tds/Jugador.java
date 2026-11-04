package uva.tds;

public class Jugador {
    private String nombre;

    public Jugador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede ser nulo ni vacío");
        }

        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}