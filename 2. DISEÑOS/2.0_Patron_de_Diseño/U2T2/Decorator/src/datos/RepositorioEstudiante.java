package datos;
import java.util.*;
import modelo.Estudiante;

/** Colección en memoria; no contiene la regla académica. */
public final class RepositorioEstudiante {
    private final Map<String, Estudiante> registros = new LinkedHashMap<>();
    public void guardar(Estudiante e) {
        if (registros.containsKey(e.getId())) throw new IllegalArgumentException("El ID ya existe.");
        registros.put(e.getId(), e);
    }
    public Estudiante buscar(String id) {
        Estudiante e = registros.get(id);
        if (e == null) throw new IllegalArgumentException("No existe el estudiante con ID " + id);
        return e;
    }
    public void actualizar(Estudiante e) { buscar(e.getId()); registros.put(e.getId(), e); }
    public void eliminar(String id) { buscar(id); registros.remove(id); }
    public List<Estudiante> listarTodos() { return List.copyOf(registros.values()); }
}
