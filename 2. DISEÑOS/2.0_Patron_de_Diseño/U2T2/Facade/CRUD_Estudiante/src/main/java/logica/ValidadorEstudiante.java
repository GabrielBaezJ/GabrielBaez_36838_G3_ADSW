package logica;

/**
 * Subsistema de validaciones de entrada y datos obligatorios (RF-05).
 */
public class ValidadorEstudiante {

    public void validarId(String id) throws IllegalArgumentException {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: el ID no puede estar vacio.");
        }
    }

    public void validarNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: el nombre no puede estar vacio.");
        }
        if (!nombre.matches("[\\p{L} ]+")) {
            throw new IllegalArgumentException("Error: ingrese solo letras en el nombre.");
        }
    }

    public void validarEdad(int edad) throws IllegalArgumentException {
        if (edad < 1 || edad > 120) {
            throw new IllegalArgumentException("Error: edad invalida (debe estar entre 1 y 120).");
        }
    }

    public void validarDatos(String id, String nombre, int edad) throws IllegalArgumentException {
        validarId(id);
        validarNombre(nombre);
        validarEdad(edad);
    }
}
