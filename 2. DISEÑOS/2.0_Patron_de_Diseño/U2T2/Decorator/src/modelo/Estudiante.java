package modelo;

/** Entidad inmutable: los cambios crean una nueva instancia. */
public final class Estudiante {
    private final String id;
    private final String nombre;
    private final int edad;
    public Estudiante(String id, String nombre, int edad) {
        if (id == null || !id.matches("L[0-9]{8}"))
            throw new IllegalArgumentException("El ID debe tener L mayúscula seguida de 8 dígitos, ejemplo L00423176.");
        if (edad <= 0 || nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Edad positiva y nombre obligatorio.");
        this.id = id; this.nombre = nombre.strip(); this.edad = edad;
    }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    @Override public String toString() {
        return "ID=" + id + ", Nombre=" + nombre + ", Edad=" + edad;
    }
}
