package presentacion;

import fachada.EstudianteFachada;
import logica.ResultadoOperacion;
import modelo.Estudiante;

/**
 * Clase de prueba automatizada para consola que verifica el cumplimiento de los requisitos
 * RF-01 a RF-05 y la regla académica utilizando el patrón de diseño Facade (Fachada).
 */
public class PruebaCrudEstudiantes {

    public static void main(String[] args) {
        // La capa de presentación solo interactúa con la Fachada
        EstudianteFachada fachada = new EstudianteFachada();

        // RF-01: Registrar
        System.out.println("=== RF-01 Registrar ===");
        ResultadoOperacion r1 = fachada.registrar("1", "Ana Torres", 20);
        System.out.println(r1.getMensaje());

        ResultadoOperacion r2 = fachada.registrar("2", "Luis Perez", 17);
        System.out.println(r2.getMensaje());

        // RF-04: Consultar
        System.out.println("\n=== RF-04 Consultar ===");
        for (Estudiante e : fachada.listarTodos()) {
            System.out.println(e);
        }

        // RF-02: Actualizar
        System.out.println("\n=== RF-02 Actualizar ===");
        ResultadoOperacion r3 = fachada.actualizar("1", "Ana Torres Actualizada", 20);
        System.out.println(r3.getMensaje());

        // RF-03: Eliminar aplicando regla académica
        System.out.println("\n=== RF-03 Eliminar ===");
        // Intento de eliminar menor de 18 años (debe ser bloqueado por la regla académica)
        ResultadoOperacion r4 = fachada.eliminar("2");
        System.out.println(r4.getMensaje());

        // Eliminación válida de mayor de edad
        ResultadoOperacion r5 = fachada.eliminar("1");
        System.out.println(r5.getMensaje());

        // Lista final resultante
        System.out.println("\n=== Lista final ===");
        for (Estudiante e : fachada.listarTodos()) {
            System.out.println(e);
        }
    }
}
