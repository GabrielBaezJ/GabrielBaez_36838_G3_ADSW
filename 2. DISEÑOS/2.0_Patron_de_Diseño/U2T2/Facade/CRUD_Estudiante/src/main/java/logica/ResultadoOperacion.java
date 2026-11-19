package logica;

import modelo.Estudiante;

/**
 * Encapsula la respuesta de las operaciones ejecutadas por la Fachada hacia la capa de presentación.
 */
public class ResultadoOperacion {

    private final boolean exitoso;
    private final String mensaje;
    private final Estudiante estudiante;

    public ResultadoOperacion(boolean exitoso, String mensaje, Estudiante estudiante) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.estudiante = estudiante;
    }

    public static ResultadoOperacion ok(String mensaje, Estudiante estudiante) {
        return new ResultadoOperacion(true, mensaje, estudiante);
    }

    public static ResultadoOperacion ok(String mensaje) {
        return new ResultadoOperacion(true, mensaje, null);
    }

    public static ResultadoOperacion error(String mensaje) {
        return new ResultadoOperacion(false, mensaje, null);
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }
}
