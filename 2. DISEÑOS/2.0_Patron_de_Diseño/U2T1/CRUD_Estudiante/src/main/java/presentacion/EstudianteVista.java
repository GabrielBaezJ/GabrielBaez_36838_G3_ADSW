
package presentacion;

import java.util.List;
import java.util.Scanner;
import modelo.Estudiante;

public class EstudianteVista {

    private final Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("\n===== CRUD ESTUDIANTES =====");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Listar estudiantes");
        System.out.println("3. Buscar estudiante");
        System.out.println("4. Actualizar estudiante");
        System.out.println("5. Eliminar estudiante");
        System.out.println("6. Salir");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                mostrarMensaje("Error: ingrese un numero entero.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarEstudiante(Estudiante e) {
        System.out.println(
            "ID: " + e.getId()
            + " | Nombre: " + e.getNombre()
            + " | Edad: " + e.getEdad()
        );
    }

    public void listarEstudiantes(List<Estudiante> estudiantes) {
        if (estudiantes.isEmpty()) {
            mostrarMensaje("No hay estudiantes registrados.");
            return;
        }

        System.out.println("\n--- LISTA DE ESTUDIANTES ---");

        for (Estudiante e : estudiantes) {
            mostrarEstudiante(e);
        }
    }
}
