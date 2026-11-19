package presentacion;
import javax.swing.SwingUtilities;
import logica.ControlEstudiante;
import datos.RepositorioEstudiante;
import logica.decorator.AuditoriaDecorator;
public final class Main {
    public static void main(String[] args) {
        AuditoriaDecorator servicio = new AuditoriaDecorator(new ControlEstudiante(new RepositorioEstudiante()));
        SwingUtilities.invokeLater(() -> new CRUDEstudiantesGUI(servicio, "CRUD estudiantes — Decorator",
            null, servicio::verAuditoria).setVisible(true));
    }
}
