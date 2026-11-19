package hoja;

import componente.EstudianteComponent;

/**
 * Clase Hoja (Leaf) en el patrón Composite.
 * Representa a un estudiante individual del dominio. No tiene hijos.
 */
public class Estudiante extends EstudianteComponent {

    private String id;
    private String nombre;
    private int edad;

    public Estudiante(String id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
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
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public void imprimir() {
        System.out.println(toString());
    }

    @Override
    public String mostrarDetalles(String indentacion) {
        return indentacion + "├── [Estudiante] ID: " + id + " | Nombre: " + nombre + " | Edad: " + edad + " años\n";
    }

    @Override
    public int contarEstudiantes() {
        return 1;
    }

    @Override
    public double obtenerPromedioEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return "Estudiante [ID=" + id + ", Nombre=" + nombre + ", Edad=" + edad + "]";
    }
}
