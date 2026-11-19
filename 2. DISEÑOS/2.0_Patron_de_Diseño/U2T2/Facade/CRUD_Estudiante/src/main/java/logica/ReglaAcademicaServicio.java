package logica;

import modelo.Estudiante;

/**
 * Subsistema de reglas de negocio académicas.
 * Aplica políticas institucionales como la restricción de eliminación para menores de edad (RF-03).
 */
public class ReglaAcademicaServicio {

    private static final int EDAD_MINIMA_ELIMINACION = 18;

    /**
     * RF-03: Evalúa si un estudiante cumple la regla académica para poder ser eliminado.
     * No se permite eliminar estudiantes menores de 18 años.
     */
    public boolean puedeEliminar(Estudiante estudiante) {
        if (estudiante == null) {
            return false;
        }
        return estudiante.getEdad() >= EDAD_MINIMA_ELIMINACION;
    }

    /**
     * Retorna el mensaje de error oficial establecido en la regla académica.
     */
    public String obtenerMensajeRestriccion(Estudiante estudiante) {
        return "Error: Regla academica - no se puede eliminar a '"
                + estudiante.getNombre()
                + "' porque es menor de 18 anios (Edad: "
                + estudiante.getEdad() + ").";
    }
}
