package logica;

import componente.EstudianteComponent;
import compuesto.GrupoEstudiantes;
import datos.EstudianteRepositorio;
import hoja.Estudiante;
import java.util.List;

/**
 * Capa de servicio / lógica de negocio.
 * Aplica validaciones obligatorias (RF-05), reglas académicas (RF-03)
 * y coordina las operaciones CRUD sobre la estructura Composite de estudiantes.
 */
public class EstudianteService {

    private final EstudianteRepositorio repositorio;

    public EstudianteService() {
        this.repositorio = new EstudianteRepositorio();
    }

    public EstudianteService(EstudianteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * RF-01: Registrar estudiante individual (Hoja).
     */
    public ResultadoOperacion registrarEstudiante(String id, String nombre, int edad) {
        ResultadoOperacion validacion = validarDatosEstudiante(id, nombre, edad);
        if (!validacion.isExitoso()) {
            return validacion;
        }

        if (repositorio.existe(id)) {
            return ResultadoOperacion.error("Ya existe un componente registrado con el ID: " + id);
        }

        Estudiante estudiante = new Estudiante(id.trim(), nombre.trim(), edad);
        repositorio.guardar(estudiante);
        return ResultadoOperacion.exito("Estudiante registrado exitosamente: " + estudiante.getNombre());
    }

    /**
     * Registrar un grupo compuesto (Composite).
     */
    public ResultadoOperacion registrarGrupo(String id, String nombreGrupo) {
        if (id == null || id.trim().isEmpty()) {
            return ResultadoOperacion.error("El ID del grupo es obligatorio.");
        }
        if (nombreGrupo == null || nombreGrupo.trim().isEmpty()) {
            return ResultadoOperacion.error("El nombre del grupo es obligatorio.");
        }
        if (repositorio.existe(id)) {
            return ResultadoOperacion.error("Ya existe un componente con el ID: " + id);
        }

        GrupoEstudiantes grupo = new GrupoEstudiantes(id.trim(), nombreGrupo.trim());
        repositorio.guardar(grupo);
        return ResultadoOperacion.exito("Grupo creado exitosamente: " + grupo.getNombre());
    }

    /**
     * Agregar un componente (estudiante o subgrupo) a un grupo contenedor.
     */
    public ResultadoOperacion agregarAGrupo(String idGrupo, String idElemento) {
        EstudianteComponent contenedor = repositorio.buscarPorId(idGrupo);
        if (contenedor == null) {
            return ResultadoOperacion.error("No se encontró el grupo contenedor con ID: " + idGrupo);
        }
        if (!contenedor.esCompuesto()) {
            return ResultadoOperacion.error("El elemento con ID " + idGrupo + " es un estudiante individual y no puede contener otros elementos.");
        }

        EstudianteComponent elemento = repositorio.buscarPorId(idElemento);
        if (elemento == null) {
            return ResultadoOperacion.error("No se encontró el elemento a agregar con ID: " + idElemento);
        }

        if (idGrupo.equalsIgnoreCase(idElemento)) {
            return ResultadoOperacion.error("Un grupo no puede agregarse a sí mismo.");
        }

        contenedor.agregar(elemento);
        return ResultadoOperacion.exito("Elemento '" + elemento.getNombre() + "' agregado satisfactoriamente al grupo '" + contenedor.getNombre() + "'.");
    }

    /**
     * RF-02: Actualizar datos de un estudiante existente.
     */
    public ResultadoOperacion actualizarEstudiante(String id, String nuevoNombre, int nuevaEdad) {
        if (id == null || id.trim().isEmpty()) {
            return ResultadoOperacion.error("El ID es obligatorio para actualizar.");
        }
        EstudianteComponent componente = repositorio.buscarPorId(id);
        if (componente == null) {
            return ResultadoOperacion.error("No existe ningún estudiante registrado con ID: " + id);
        }

        if (componente.esCompuesto()) {
            // Actualización de grupo
            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                return ResultadoOperacion.error("El nuevo nombre del grupo no puede estar vacío.");
            }
            ((GrupoEstudiantes) componente).setNombre(nuevoNombre.trim());
            return ResultadoOperacion.exito("Grupo ID " + id + " actualizado exitosamente.");
        }

        ResultadoOperacion validacion = validarDatosEstudiante(id, nuevoNombre, nuevaEdad);
        if (!validacion.isExitoso()) {
            return validacion;
        }

        Estudiante estudiante = (Estudiante) componente;
        estudiante.setNombre(nuevoNombre.trim());
        estudiante.setEdad(nuevaEdad);
        return ResultadoOperacion.exito("Estudiante ID " + id + " actualizado exitosamente.");
    }

    /**
     * RF-03: Eliminar estudiante o grupo aplicando la regla académica.
     * Regla académica didáctica: No eliminar estudiantes menores de 18 años.
     */
    public ResultadoOperacion eliminar(String id) {
        if (id == null || id.trim().isEmpty()) {
            return ResultadoOperacion.error("El ID es obligatorio para eliminar.");
        }

        EstudianteComponent componente = repositorio.buscarPorId(id);
        if (componente == null) {
            return ResultadoOperacion.error("No existe ningún elemento registrado con ID: " + id);
        }

        // Si es una hoja (estudiante individual), aplicar regla académica
        if (!componente.esCompuesto()) {
            if (componente.getEdad() < 18) {
                return ResultadoOperacion.error("Regla académica - no se puede eliminar a '" + componente.getNombre()
                        + "' porque es menor de 18 años (Edad: " + componente.getEdad() + ").");
            }
        }

        boolean eliminado = repositorio.eliminar(id);
        if (eliminado) {
            return ResultadoOperacion.exito("Eliminado exitosamente: " + componente.getNombre() + " (ID: " + id + ")");
        } else {
            return ResultadoOperacion.error("No se pudo eliminar el elemento con ID: " + id);
        }
    }

    /**
     * RF-04: Consultar estudiante o grupo por ID.
     */
    public EstudianteComponent consultarPorId(String id) {
        return repositorio.buscarPorId(id);
    }

    /**
     * RF-04: Listar todos los componentes registrados en la raíz.
     */
    public List<EstudianteComponent> listarRaiz() {
        return repositorio.listarRaiz();
    }

    /**
     * RF-04: Listar todos los componentes (planos, tanto raíces como anidados).
     */
    public List<EstudianteComponent> listarTodos() {
        return repositorio.listarTodosPlano();
    }

    /**
     * Muestra la estructura de árbol completa en formato textual.
     */
    public String obtenerArbolCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTRUCTURA JERÁRQUICA COMPOSITE DE ESTUDIANTES ===\n");
        for (EstudianteComponent raiz : repositorio.listarRaiz()) {
            sb.append(raiz.mostrarDetalles(""));
        }
        return sb.toString();
    }

    /**
     * RF-05: Validación de datos obligatorios.
     */
    private ResultadoOperacion validarDatosEstudiante(String id, String nombre, int edad) {
        if (id == null || id.trim().isEmpty()) {
            return ResultadoOperacion.error("El ID del estudiante es obligatorio.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            return ResultadoOperacion.error("El nombre del estudiante es obligatorio.");
        }
        if (edad <= 0) {
            return ResultadoOperacion.error("La edad debe ser un número entero positivo mayor a cero.");
        }
        return ResultadoOperacion.exito("Datos válidos.");
    }
}
