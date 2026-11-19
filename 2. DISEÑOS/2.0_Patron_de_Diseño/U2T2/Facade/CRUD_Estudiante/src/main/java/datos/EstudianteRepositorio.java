
package datos;

import java.util.ArrayList;
import java.util.List;
import modelo.Estudiante;

public class EstudianteRepositorio {

    private final List<Estudiante> estudiantes = new ArrayList<>();

    // CREATE
    public void guardar(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    // READ
    public List<Estudiante> listar() {
        return new ArrayList<>(estudiantes);
    }

    public List<Estudiante> listarTodos() {
        return listar();
    }

    public Estudiante buscarPorId(String id) {
        for (Estudiante e : estudiantes) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    // UPDATE
    public boolean actualizar(String id, String nombre, int edad) {
        Estudiante e = buscarPorId(id);

        if (e == null) {
            return false;
        }

        e.setNombre(nombre);
        e.setEdad(edad);
        return true;
    }

    // DELETE
    public boolean eliminar(String id) {
        Estudiante e = buscarPorId(id);

        if (e == null) {
            return false;
        }

        return estudiantes.remove(e);
    }
}
