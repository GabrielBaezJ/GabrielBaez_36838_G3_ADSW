
package logica;

import datos.EstudianteRepositorio;
import modelo.Estudiante;
import presentacion.EstudianteVista;

public class EstudianteControlador {

    private final EstudianteRepositorio repositorio;
    private final EstudianteVista vista;

    public EstudianteControlador(
            EstudianteRepositorio repositorio,
            EstudianteVista vista) {

        this.repositorio = repositorio;
        this.vista = vista;
    }

    // VALIDAR ID: L + 8 numeros
    private boolean validarId(String id) {
        return id.matches("L\\d{8}");
    }

    // VALIDAR NOMBRE
    private boolean validarNombre(String nombre) {
        return nombre.matches("[\\p{L} ]+");
    }

    // VALIDAR EDAD
    private boolean validarEdad(int edad) {
        return edad >= 1 && edad <= 120;
    }

    // Solicitar ID valido
    private String solicitarId() {
        String id;

        do {
            id = vista.leerTexto("Ingrese ID (L00123456): ")
                    .toUpperCase();

            if (!validarId(id)) {
                vista.mostrarMensaje(
                    "Error: el ID debe tener L y 8 numeros."
                );
            }
        } while (!validarId(id));

        return id;
    }

    // Solicitar nombre valido
    private String solicitarNombre() {
        String nombre;

        do {
            nombre = vista.leerTexto("Ingrese nombre: ");

            if (!validarNombre(nombre)) {
                vista.mostrarMensaje(
                    "Error: ingrese solo letras."
                );
            }
        } while (!validarNombre(nombre));

        return nombre;
    }

    // Solicitar edad valida
    private int solicitarEdad() {
        int edad;

        do {
            edad = vista.leerEntero("Ingrese edad: ");

            if (!validarEdad(edad)) {
                vista.mostrarMensaje(
                    "Error: edad entre 1 y 120."
                );
            }
        } while (!validarEdad(edad));

        return edad;
    }

    // CREATE
    public void registrar() {
        String id = solicitarId();

        if (repositorio.buscarPorId(id) != null) {
            vista.mostrarMensaje("Error: el ID ya existe.");
            return;
        }

        String nombre = solicitarNombre();
        int edad = solicitarEdad();

        Estudiante estudiante = new Estudiante(id, nombre, edad);
        repositorio.guardar(estudiante);

        vista.mostrarMensaje("Estudiante registrado correctamente.");
    }

    // READ: LISTAR
    public void listar() {
        vista.listarEstudiantes(repositorio.listar());
    }

    // READ: BUSCAR
    public void buscar() {
        String id = solicitarId();
        Estudiante e = repositorio.buscarPorId(id);

        if (e == null) {
            vista.mostrarMensaje("Estudiante no encontrado.");
        } else {
            vista.mostrarEstudiante(e);
        }
    }

    // UPDATE
    public void actualizar() {
        String id = solicitarId();

        if (repositorio.buscarPorId(id) == null) {
            vista.mostrarMensaje("Estudiante no encontrado.");
            return;
        }

        String nombre = solicitarNombre();
        int edad = solicitarEdad();

        repositorio.actualizar(id, nombre, edad);
        vista.mostrarMensaje("Estudiante actualizado correctamente.");
    }

    // DELETE
    public void eliminar() {
        String id = solicitarId();

        if (repositorio.eliminar(id)) {
            vista.mostrarMensaje("Estudiante eliminado correctamente.");
        } else {
            vista.mostrarMensaje("Estudiante no encontrado.");
        }
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
