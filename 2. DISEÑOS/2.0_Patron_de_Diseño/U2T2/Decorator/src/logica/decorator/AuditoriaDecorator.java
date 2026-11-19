package logica.decorator;
import java.util.*;
import logica.OperacionesEstudiante;

/** Añade una bitácora en memoria de las mutaciones exitosas. */
public final class AuditoriaDecorator extends EstudianteDecorator {
    private final List<String> eventos = new ArrayList<>();
    public AuditoriaDecorator(OperacionesEstudiante envuelto) { super(envuelto); }
    @Override public void agregarEstudiante(String id, String nombre, String edad) {
        super.agregarEstudiante(id, nombre, edad); eventos.add("REGISTRAR ID=" + id.strip());
    }
    @Override public void actualizarEstudiante(String id, String nombre, String edad) {
        super.actualizarEstudiante(id, nombre, edad); eventos.add("ACTUALIZAR ID=" + id.strip());
    }
    @Override public void eliminarEstudiante(String id) {
        super.eliminarEstudiante(id); eventos.add("ELIMINAR ID=" + id.strip());
    }
    public List<String> getEventos() { return List.copyOf(eventos); }
    public String verAuditoria() { return eventos.isEmpty() ? "Sin operaciones exitosas." : String.join("\n", eventos); }
}
