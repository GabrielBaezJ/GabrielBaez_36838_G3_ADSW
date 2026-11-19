package compuesto;

import componente.EstudianteComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase Compuesta (Composite) en el patrón Composite.
 * Representa una agrupación de estudiantes (curso, paralelo, equipo de trabajo, etc.).
 * Puede contener tanto Estudiantes (hojas) como otros GrupoEstudiantes (compuestos).
 */
public class GrupoEstudiantes extends EstudianteComponent {

    private String id;
    private String nombreGrupo;
    private final List<EstudianteComponent> elementos = new ArrayList<>();

    public GrupoEstudiantes(String id, String nombreGrupo) {
        this.id = id;
        this.nombreGrupo = nombreGrupo;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return nombreGrupo;
    }

    public void setNombre(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    @Override
    public int getEdad() {
        return (int) Math.round(obtenerPromedioEdad());
    }

    @Override
    public void agregar(EstudianteComponent componente) {
        if (componente != null && !elementos.contains(componente)) {
            elementos.add(componente);
        }
    }

    @Override
    public void eliminar(EstudianteComponent componente) {
        elementos.remove(componente);
    }

    @Override
    public EstudianteComponent obtenerHijo(int indice) {
        if (indice >= 0 && indice < elementos.size()) {
            return elementos.get(indice);
        }
        return null;
    }

    @Override
    public List<EstudianteComponent> obtenerHijos() {
        return Collections.unmodifiableList(elementos);
    }

    @Override
    public boolean esCompuesto() {
        return true;
    }

    @Override
    public int contarEstudiantes() {
        int total = 0;
        for (EstudianteComponent c : elementos) {
            total += c.contarEstudiantes();
        }
        return total;
    }

    @Override
    public double obtenerPromedioEdad() {
        int totalEstudiantes = contarEstudiantes();
        if (totalEstudiantes == 0) {
            return 0.0;
        }
        double sumaEdades = sumarEdadesRecursivo(this);
        return sumaEdades / totalEstudiantes;
    }

    private double sumarEdadesRecursivo(EstudianteComponent componente) {
        double suma = 0.0;
        if (!componente.esCompuesto()) {
            suma += componente.getEdad();
        } else {
            for (EstudianteComponent hijo : componente.obtenerHijos()) {
                suma += sumarEdadesRecursivo(hijo);
            }
        }
        return suma;
    }

    @Override
    public void imprimir() {
        System.out.print(mostrarDetalles(""));
    }

    @Override
    public String mostrarDetalles(String indentacion) {
        StringBuilder sb = new StringBuilder();
        sb.append(indentacion)
          .append("📁 [Grupo] ID: ").append(id)
          .append(" | Nombre: ").append(nombreGrupo)
          .append(" | Cantidad alumnos: ").append(contarEstudiantes())
          .append(String.format(" | Promedio edad: %.1f años\n", obtenerPromedioEdad()));
        for (EstudianteComponent c : elementos) {
            sb.append(c.mostrarDetalles(indentacion + "    "));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "GrupoEstudiantes [ID=" + id + ", Nombre=" + nombreGrupo + ", Miembros=" + elementos.size() + "]";
    }
}
