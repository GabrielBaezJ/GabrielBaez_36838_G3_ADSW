package logica;
import java.util.List;
import datos.RepositorioEstudiante;
import modelo.Estudiante;

/** Lógica de negocio para IDs académicos de texto. */
public final class ControlEstudiante implements OperacionesEstudiante {
    private final RepositorioEstudiante repositorio;
    public ControlEstudiante(RepositorioEstudiante repositorio) { this.repositorio = repositorio; }
    private String validarId(String id) {
        if (id == null || !id.strip().matches("L[0-9]{8}"))
            throw new IllegalArgumentException("El ID debe tener L mayúscula seguida de 8 dígitos, ejemplo L00423176.");
        return id.strip();
    }
    private int enteroPositivo(String texto, String campo) {
        if (texto == null || !texto.strip().matches("[0-9]+"))
            throw new IllegalArgumentException(campo + " debe ser un entero positivo.");
        try {
            int valor = Integer.parseInt(texto.strip());
            if (valor <= 0) throw new NumberFormatException();
            return valor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(campo + " debe estar entre 1 y 2147483647.");
        }
    }
    private Estudiante validarDatos(String id, String nombre, String edad) {
        return new Estudiante(validarId(id), nombre, enteroPositivo(edad, "Edad"));
    }
    @Override public void agregarEstudiante(String id, String nombre, String edad) {
        repositorio.guardar(validarDatos(id, nombre, edad));
    }
    @Override public void actualizarEstudiante(String id, String nombre, String edad) {
        repositorio.actualizar(validarDatos(id, nombre, edad));
    }
    @Override public void eliminarEstudiante(String id) {
        Estudiante e = repositorio.buscar(validarId(id));
        if (e.getEdad() < 18) throw new IllegalArgumentException("Regla académica: no se puede eliminar a menores de 18 años.");
        repositorio.eliminar(e.getId());
    }
    @Override public List<Estudiante> listarTodos() { return repositorio.listarTodos(); }
}
