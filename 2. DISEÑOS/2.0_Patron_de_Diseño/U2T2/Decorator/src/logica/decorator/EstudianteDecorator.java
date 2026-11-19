package logica.decorator;
import java.util.List;
import logica.OperacionesEstudiante;
import modelo.Estudiante;

/** Decorador base: mantiene el contrato y delega en el objeto envuelto. */
public abstract class EstudianteDecorator implements OperacionesEstudiante {
    protected final OperacionesEstudiante envuelto;
    protected EstudianteDecorator(OperacionesEstudiante envuelto) { this.envuelto = java.util.Objects.requireNonNull(envuelto); }
    @Override public void agregarEstudiante(String id, String nombre, String edad) { envuelto.agregarEstudiante(id, nombre, edad); }
    @Override public void actualizarEstudiante(String id, String nombre, String edad) { envuelto.actualizarEstudiante(id, nombre, edad); }
    @Override public void eliminarEstudiante(String id) { envuelto.eliminarEstudiante(id); }
    @Override public List<Estudiante> listarTodos() { return envuelto.listarTodos(); }
}
