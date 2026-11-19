package componente;

import java.util.Collections;
import java.util.List;

/**
 * Componente base de la jerarquía Composite.
 * Define la interfaz común tanto para objetos simples (Estudiante / Hoja)
 * como para objetos compuestos (GrupoEstudiantes / Composite).
 */
public abstract class EstudianteComponent {

    public abstract String getId();

    public abstract String getNombre();

    public abstract int getEdad();

    public abstract void imprimir();

    public abstract String mostrarDetalles(String indentacion);

    public abstract int contarEstudiantes();

    public abstract double obtenerPromedioEdad();

    // Métodos de gestión de hijos con comportamiento por defecto (para hojas)
    public void agregar(EstudianteComponent componente) {
        throw new UnsupportedOperationException("Operación no soportada en un nodo hoja (Estudiante individual).");
    }

    public void eliminar(EstudianteComponent componente) {
        throw new UnsupportedOperationException("Operación no soportada en un nodo hoja (Estudiante individual).");
    }

    public EstudianteComponent obtenerHijo(int indice) {
        throw new UnsupportedOperationException("Operación no soportada en un nodo hoja (Estudiante individual).");
    }

    public List<EstudianteComponent> obtenerHijos() {
        return Collections.emptyList();
    }

    public boolean esCompuesto() {
        return false;
    }
}
