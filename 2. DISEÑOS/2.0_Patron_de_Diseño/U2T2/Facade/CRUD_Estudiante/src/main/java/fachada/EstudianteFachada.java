package fachada;

import datos.EstudianteRepositorio;
import java.util.List;
import logica.ReglaAcademicaServicio;
import logica.ResultadoOperacion;
import logica.ValidadorEstudiante;
import modelo.Estudiante;

/**
 * Patrón Estructural FACADE (Fachada).
 * Proporciona una interfaz unificada y simplificada para la capa de presentación (GUI, Consola y Pruebas),
 * ocultando la complejidad e interacción entre los subsistemas de persistencia (Repositorio),
 * validación técnica (ValidadorEstudiante) y políticas del dominio (ReglaAcademicaServicio).
 */
public class EstudianteFachada {

    private final EstudianteRepositorio repositorio;
    private final ValidadorEstudiante validador;
    private final ReglaAcademicaServicio reglaAcademica;

    public EstudianteFachada() {
        this.repositorio = new EstudianteRepositorio();
        this.validador = new ValidadorEstudiante();
        this.reglaAcademica = new ReglaAcademicaServicio();
    }

    public EstudianteFachada(
            EstudianteRepositorio repositorio,
            ValidadorEstudiante validador,
            ReglaAcademicaServicio reglaAcademica) {
        this.repositorio = repositorio;
        this.validador = validador;
        this.reglaAcademica = reglaAcademica;
    }

    /**
     * RF-01 / RF-05: Valida y registra un nuevo estudiante si no existe duplicado.
     */
    public ResultadoOperacion registrar(String id, String nombre, int edad) {
        try {
            validador.validarDatos(id, nombre, edad);
        } catch (IllegalArgumentException e) {
            return ResultadoOperacion.error(e.getMessage());
        }

        String idNormalizado = id.trim();
        if (repositorio.buscarPorId(idNormalizado) != null) {
            return ResultadoOperacion.error("Error: ya existe un estudiante con ID: " + idNormalizado);
        }

        Estudiante nuevo = new Estudiante(idNormalizado, nombre.trim(), edad);
        repositorio.guardar(nuevo);
        return ResultadoOperacion.ok("Agregado exitosamente: " + nuevo.getNombre(), nuevo);
    }

    /**
     * Alias compatible con especificación ERS/SRS.
     */
    public ResultadoOperacion agregarEstudiante(String id, String nombre, int edad) {
        return registrar(id, nombre, edad);
    }

    /**
     * RF-02 / RF-05: Valida y actualiza los datos de un estudiante existente.
     */
    public ResultadoOperacion actualizar(String id, String nombre, int edad) {
        try {
            validador.validarDatos(id, nombre, edad);
        } catch (IllegalArgumentException e) {
            return ResultadoOperacion.error(e.getMessage());
        }

        String idNormalizado = id.trim();
        Estudiante existente = repositorio.buscarPorId(idNormalizado);
        if (existente == null) {
            return ResultadoOperacion.error("Error: estudiante no encontrado con ID " + idNormalizado);
        }

        repositorio.actualizar(idNormalizado, nombre.trim(), edad);
        return ResultadoOperacion.ok("Actualizado exitosamente: ID " + idNormalizado, existente);
    }

    /**
     * Alias compatible con especificación ERS/SRS.
     */
    public ResultadoOperacion actualizarEstudiante(String id, String nombre, int edad) {
        return actualizar(id, nombre, edad);
    }

    /**
     * RF-03: Elimina un estudiante evaluando previamente la regla académica de mayoría de edad.
     */
    public ResultadoOperacion eliminar(String id) {
        if (id == null || id.trim().isEmpty()) {
            return ResultadoOperacion.error("Error: el ID no puede estar vacio.");
        }

        String idNormalizado = id.trim();
        Estudiante estudiante = repositorio.buscarPorId(idNormalizado);
        if (estudiante == null) {
            return ResultadoOperacion.error("Error: estudiante no encontrado con ID " + idNormalizado);
        }

        if (!reglaAcademica.puedeEliminar(estudiante)) {
            return ResultadoOperacion.error(reglaAcademica.obtenerMensajeRestriccion(estudiante));
        }

        String nombreEliminado = estudiante.getNombre();
        repositorio.eliminar(idNormalizado);
        return ResultadoOperacion.ok("Eliminado exitosamente: " + nombreEliminado + " (ID " + idNormalizado + ")", estudiante);
    }

    /**
     * Alias compatible con especificación ERS/SRS.
     */
    public ResultadoOperacion eliminarEstudiante(String id) {
        return eliminar(id);
    }

    /**
     * RF-04: Consulta y lista todos los estudiantes registrados.
     */
    public List<Estudiante> listarTodos() {
        return repositorio.listar();
    }

    public List<Estudiante> listar() {
        return listarTodos();
    }

    /**
     * Consulta un estudiante por su identificador.
     */
    public Estudiante buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return repositorio.buscarPorId(id.trim());
    }

    public Estudiante buscarEstudiante(String id) {
        return buscarPorId(id);
    }

    /**
     * RF-05: Validación directa de datos obligatorios delegada al subsistema validador.
     */
    public void validarDatos(String id, String nombre, int edad) throws IllegalArgumentException {
        validador.validarDatos(id, nombre, edad);
    }

    public EstudianteRepositorio getRepositorio() {
        return repositorio;
    }
}
