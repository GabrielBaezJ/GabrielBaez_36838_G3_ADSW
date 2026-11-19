package pruebas;
import java.util.*;
import logica.*;
import datos.RepositorioEstudiante;
import modelo.Estudiante;
import logica.decorator.*;

/** Pruebas sin dependencias; fallan con AssertionError incluso sin -ea. */
public final class PruebaCrudEstudiantes {
    private static int total;
    private static void verificar(boolean condicion, String caso) {
        if (!condicion) throw new AssertionError(caso);
        total++; System.out.println("OK " + caso);
    }
    private static void rechazar(Runnable accion, String caso) {
        try { accion.run(); } catch (IllegalArgumentException e) { verificar(true, caso); return; }
        throw new AssertionError("Se aceptó una operación inválida: " + caso);
    }
    public static void main(String[] args) {
        AuditoriaDecorator servicio = new AuditoriaDecorator(new ControlEstudiante(new RepositorioEstudiante()));
        verificar(servicio.listarTodos().isEmpty(), "RF04 lista vacía");
        servicio.agregarEstudiante("L00423176", " Ana Torres ", "20");
        verificar(servicio.listarTodos().get(0).getNombre().equals("Ana Torres"), "RF01 registrar y normalizar");
        rechazar(() -> servicio.agregarEstudiante("L00423176", "Duplicado", "19"), "RF01 ID duplicado");
        String[][] invalidos = {{"", "Ana", "20"}, {"0", "Ana", "20"}, {"abc", "Ana", "20"}, {"L00423177", " ", "20"}, {"L00423177", "Ana", ""}, {"L00423177", "Ana", "0"}, {"L00423177", "Ana", "-1"}, {"L00423177", "Ana", "1.5"}, {"999999999999", "Ana", "20"}};
        for (String[] d : invalidos) rechazar(() -> servicio.agregarEstudiante(d[0], d[1], d[2]), "RF05 " + Arrays.toString(d));
        rechazar(() -> servicio.agregarEstudiante(null, "Ana", "20"), "RF05 ID null");
        rechazar(() -> servicio.agregarEstudiante("L00423177", null, "20"), "RF05 nombre null");
        rechazar(() -> servicio.agregarEstudiante("L00423177", "Ana", null), "RF05 edad null");
        verificar(servicio.listarTodos().size() == 1, "RF05 fallos sin inserciones");
        servicio.actualizarEstudiante("L00423176", "Ana Actualizada", "21");
        verificar(servicio.listarTodos().get(0).getEdad() == 21, "RF02 actualizar");
        rechazar(() -> servicio.actualizarEstudiante("L00423176", "", "22"), "RF05 actualización inválida");
        verificar(servicio.listarTodos().get(0).getNombre().equals("Ana Actualizada"), "RF05 preserva cambios previos");
        rechazar(() -> servicio.actualizarEstudiante("L99999999", "Ana", "22"), "RF02 inexistente");
        rechazar(() -> servicio.eliminarEstudiante("L99999999"), "RF03 inexistente");
        servicio.agregarEstudiante("L00423177", "Luis Pérez", "17");
        rechazar(() -> servicio.eliminarEstudiante("L00423177"), "RF03 protege menor");
        verificar(servicio.listarTodos().size() == 2, "RF04 listado completo");
        servicio.eliminarEstudiante("L00423176");
        verificar(servicio.listarTodos().size() == 1, "RF03 elimina adulto");
        servicio.actualizarEstudiante("L00423177", "Luis Pérez", "18");
        servicio.eliminarEstudiante("L00423177");
        verificar(servicio.listarTodos().isEmpty(), "RF03 límite 18 años");
        AuditoriaDecorator audit = new AuditoriaDecorator(new ControlEstudiante(new RepositorioEstudiante()));
        audit.agregarEstudiante("L00423176", "Ana", "18");
        rechazar(() -> audit.agregarEstudiante("L00423176", "Otra", "20"), "Decorator error delegado");
        verificar(audit.getEventos().size() == 1, "Decorator no registra fallos como éxitos");
        audit.actualizarEstudiante("L00423176", "Ana", "19"); audit.eliminarEstudiante("L00423176");
        verificar(audit.getEventos().equals(List.of("REGISTRAR ID=L00423176", "ACTUALIZAR ID=L00423176", "ELIMINAR ID=L00423176")), "Decorator orden de eventos");
        AuditoriaDecorator exterior = new AuditoriaDecorator(audit);
        exterior.agregarEstudiante("L00423177", "Luis", "18");
        verificar(exterior.getEventos().size() == 1 && audit.getEventos().size() == 4, "Decorator composición de envoltorios");
        OperacionesEstudiante validacion = new ControlEstudiante(new RepositorioEstudiante());
        validacion.agregarEstudiante(" L00423176 ", "Ana", "20");
        verificar(validacion.listarTodos().get(0).getId().equals("L00423176"), "ID conserva letra y ceros iniciales");
        rechazar(() -> validacion.agregarEstudiante("L00423176", "Otra", "22"), "ID académico duplicado");
        String[] idsInvalidos = {"00423176", "l00423176", "A00423176", "L0042317", "L004231760", "L00423A76", "L004 3176", "", "1"};
        for (String invalido : idsInvalidos) {
            rechazar(() -> validacion.agregarEstudiante(invalido, "Ana", "20"), "ID inválido en registro: " + invalido);
            rechazar(() -> validacion.actualizarEstudiante(invalido, "Otra", "21"), "ID inválido en actualización: " + invalido);
            rechazar(() -> validacion.eliminarEstudiante(invalido), "ID inválido en eliminación: " + invalido);
        }
        verificar(validacion.listarTodos().size() == 1 && validacion.listarTodos().get(0).getNombre().equals("Ana"), "IDs inválidos no modifican registros");
        System.out.println("RESULTADO: " + total + " verificaciones aprobadas.");
    }
}
