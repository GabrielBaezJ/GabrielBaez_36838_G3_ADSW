package logica;

/**
 * Encapsula la respuesta de las operaciones de negocio (éxito/fracaso y mensaje descriptivo).
 */
public class ResultadoOperacion {

    private final boolean exitoso;
    private final String mensaje;

    public ResultadoOperacion(boolean exitoso, String mensaje) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }

    public static ResultadoOperacion exito(String mensaje) {
        return new ResultadoOperacion(true, mensaje);
    }

    public static ResultadoOperacion error(String mensaje) {
        return new ResultadoOperacion(false, mensaje);
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return (exitoso ? "[EXITO] " : "[ERROR] ") + mensaje;
    }
}
