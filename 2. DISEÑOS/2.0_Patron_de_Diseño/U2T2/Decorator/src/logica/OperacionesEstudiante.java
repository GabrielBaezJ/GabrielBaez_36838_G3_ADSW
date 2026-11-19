package logica;
import java.util.List;
import modelo.Estudiante;

/** Contrato del CRUD utilizado por los clientes. */
public interface OperacionesEstudiante {
    void agregarEstudiante(String id, String nombre, String edad);
    void actualizarEstudiante(String id, String nombre, String edad);
    void eliminarEstudiante(String id);
    List<Estudiante> listarTodos();
}
