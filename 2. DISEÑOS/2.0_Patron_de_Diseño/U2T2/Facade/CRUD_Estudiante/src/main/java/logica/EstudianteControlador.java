package logica;

import fachada.EstudianteFachada;
import modelo.Estudiante;
import presentacion.EstudianteVista;

/**
 * Controlador de consola que consume la Fachada (EstudianteFachada),
 * permitiendo una interacción por consola interactiva que respeta el patrón Facade.
 */
public class EstudianteControlador {

    private final EstudianteFachada fachada;
    private final EstudianteVista vista;

    public EstudianteControlador(EstudianteFachada fachada, EstudianteVista vista) {
        this.fachada = fachada;
        this.vista = vista;
    }

    // CREATE (RF-01)
    public void registrar() {
        String id = vista.leerTexto("Ingrese ID: ");
        String nombre = vista.leerTexto("Ingrese nombre: ");
        int edad = vista.leerEntero("Ingrese edad: ");

        ResultadoOperacion res = fachada.registrar(id, nombre, edad);
        vista.mostrarMensaje(res.getMensaje());
    }

    // READ: LISTAR (RF-04)
    public void listar() {
        vista.listarEstudiantes(fachada.listarTodos());
    }

    // READ: BUSCAR
    public void buscar() {
        String id = vista.leerTexto("Ingrese ID a buscar: ");
        Estudiante e = fachada.buscarPorId(id);

        if (e == null) {
            vista.mostrarMensaje("Estudiante no encontrado.");
        } else {
            vista.mostrarEstudiante(e);
        }
    }

    // UPDATE (RF-02)
    public void actualizar() {
        String id = vista.leerTexto("Ingrese ID del estudiante a actualizar: ");
        Estudiante existente = fachada.buscarPorId(id);
        if (existente == null) {
            vista.mostrarMensaje("Estudiante no encontrado.");
            return;
        }

        String nombre = vista.leerTexto("Ingrese nuevo nombre: ");
        int edad = vista.leerEntero("Ingrese nueva edad: ");

        ResultadoOperacion res = fachada.actualizar(id, nombre, edad);
        vista.mostrarMensaje(res.getMensaje());
    }

    // DELETE (RF-03)
    public void eliminar() {
        String id = vista.leerTexto("Ingrese ID del estudiante a eliminar: ");
        ResultadoOperacion res = fachada.eliminar(id);
        vista.mostrarMensaje(res.getMensaje());
    }

    // MENU PRINCIPAL
    public void iniciar() {
        int opcion;

        do {
            vista.mostrarMenu();
            opcion = vista.leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> actualizar();
                case 5 -> eliminar();
                case 6 -> vista.mostrarMensaje("Saliendo...");
                default -> vista.mostrarMensaje("Opcion invalida.");
            }

        } while (opcion != 6);
    }
}
