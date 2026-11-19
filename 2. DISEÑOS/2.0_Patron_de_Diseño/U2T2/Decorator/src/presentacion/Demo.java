package presentacion;
import logica.*;
import datos.RepositorioEstudiante;
import logica.decorator.*;
public final class Demo {
    public static void main(String[] args) {
        AuditoriaDecorator servicio = new AuditoriaDecorator(new ControlEstudiante(new RepositorioEstudiante()));
        servicio.agregarEstudiante("L00423176", "Ana Torres", "20");
        servicio.agregarEstudiante("L00423177", "Luis Pérez", "17");
        servicio.actualizarEstudiante("L00423176", "Ana Torres Actualizada", "21");
        try { servicio.eliminarEstudiante("L00423177"); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        servicio.listarTodos().forEach(System.out::println);
        System.out.println(servicio.verAuditoria());
        servicio.eliminarEstudiante("L00423176");
        System.out.println("Lista final: " + servicio.listarTodos());
    }
}
