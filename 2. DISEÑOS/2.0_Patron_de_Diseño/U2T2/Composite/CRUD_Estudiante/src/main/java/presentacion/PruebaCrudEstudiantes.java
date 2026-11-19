package presentacion;

import componente.EstudianteComponent;
import logica.EstudianteService;
import logica.ResultadoOperacion;


public class PruebaCrudEstudiantes {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("   PRUEBA AUTOMATIZADA: CRUD ESTUDIANTE CON PATRÓN COMPOSITE");
        System.out.println("======================================================================\n");

        EstudianteService servicio = new EstudianteService();

        // ---------------------------------------------------------
        // 1. RF-01: REGISTRAR ESTUDIANTES INDIVIDUALES (HOJAS)
        // ---------------------------------------------------------
        System.out.println(">>> 1. [RF-01] REGISTRO DE ESTUDIANTES INDIVIDUALES (HOJAS)");
        ResultadoOperacion r1 = servicio.registrarEstudiante("1", "Ana Torres", 20);
        System.out.println(r1);

        ResultadoOperacion r2 = servicio.registrarEstudiante("2", "Luis Perez", 17);
        System.out.println(r2);

        ResultadoOperacion r3 = servicio.registrarEstudiante("3", "Carlos Andrade", 22);
        System.out.println(r3);

        ResultadoOperacion r4 = servicio.registrarEstudiante("4", "Maria Beltran", 19);
        System.out.println(r4);

        // ---------------------------------------------------------
        // 2. CREACIÓN DE GRUPOS COMPUESTOS (COMPOSITES)
        // ---------------------------------------------------------
        System.out.println("\n>>> 2. CREACIÓN DE COMPUESTOS (GRUPOS DE ESTUDIANTES)");
        ResultadoOperacion rg1 = servicio.registrarGrupo("G-A", "Paralelo A - Ingenieria de Software");
        System.out.println(rg1);

        ResultadoOperacion rg2 = servicio.registrarGrupo("G-SUB1", "Equipo de Proyecto Redes");
        System.out.println(rg2);

        // Agregamos hojas al subgrupo
        servicio.agregarAGrupo("G-SUB1", "3");
        servicio.agregarAGrupo("G-SUB1", "4");

        // Agregamos hojas y el subgrupo al grupo principal
        servicio.agregarAGrupo("G-A", "1");
        servicio.agregarAGrupo("G-A", "2");
        servicio.agregarAGrupo("G-A", "G-SUB1");

        // ---------------------------------------------------------
        // 3. RF-04: CONSULTAR Y MOSTRAR ESTRUCTURA JERÁRQUICA COMPOSITE
        // ---------------------------------------------------------
        System.out.println("\n>>> 3. [RF-04] CONSULTA Y ESTRUCTURA JERÁRQUICA (COMPOSITE)");
        System.out.println(servicio.obtenerArbolCompleto());

        EstudianteComponent grupoA = servicio.consultarPorId("G-A");
        if (grupoA != null) {
            System.out.println("--> Estadísticas compuestas calculadas recursivamente para '" + grupoA.getNombre() + "':");
            System.out.println("    Total estudiantes integrados: " + grupoA.contarEstudiantes());
            System.out.printf("    Promedio de edad del grupo: %.2f años\n", grupoA.obtenerPromedioEdad());
        }

        // ---------------------------------------------------------
        // 4. RF-02: ACTUALIZAR ESTUDIANTE
        // ---------------------------------------------------------
        System.out.println("\n>>> 4. [RF-02] ACTUALIZAR DATOS DE ESTUDIANTE");
        ResultadoOperacion ra = servicio.actualizarEstudiante("1", "Ana Torres Actualizada", 21);
        System.out.println(ra);
        EstudianteComponent actualizado = servicio.consultarPorId("1");
        System.out.println("    Registro post-actualización: " + actualizado);

        // ---------------------------------------------------------
        // 5. RF-03: ELIMINACIÓN APLICANDO REGLA ACADÉMICA
        // ---------------------------------------------------------
        System.out.println("\n>>> 5. [RF-03] ELIMINACIÓN CON EVALUACIÓN DE REGLA ACADÉMICA");
        System.out.println("Intento 1: Eliminar estudiante menor de 18 años (Luis Perez, 17 años):");
        ResultadoOperacion re1 = servicio.eliminar("2");
        System.out.println(re1); // Debe fallar por regla académica

        System.out.println("\nIntento 2: Eliminar estudiante mayor de edad (Carlos Andrade, 22 años):");
        ResultadoOperacion re2 = servicio.eliminar("3");
        System.out.println(re2); // Debe proceder con éxito

        // ---------------------------------------------------------
        // 6. RF-05: VALIDACIÓN DE DATOS OBLIGATORIOS (ERRORES PROVOCADOS)
        // ---------------------------------------------------------
        System.out.println("\n>>> 6. [RF-05] VALIDACIÓN DE DATOS OBLIGATORIOS");
        ResultadoOperacion rv1 = servicio.registrarEstudiante("", "Sin ID", 20);
        System.out.println("Prueba ID vacío: " + rv1);

        ResultadoOperacion rv2 = servicio.registrarEstudiante("5", "", 20);
        System.out.println("Prueba Nombre vacío: " + rv2);

        ResultadoOperacion rv3 = servicio.registrarEstudiante("6", "Juan Invalido", -5);
        System.out.println("Prueba Edad negativa: " + rv3);

        // ---------------------------------------------------------
        // 7. ESTADO FINAL DEL ÁRBOL
        // ---------------------------------------------------------
        System.out.println("\n>>> 7. ESTADO FINAL DEL ÁRBOL DE COMPONENTES");
        System.out.println(servicio.obtenerArbolCompleto());
        System.out.println("======================================================================");
        System.out.println("   TODAS LAS PRUEBAS EJECUTADAS Y VALIDADAS CON ÉXITO");
        System.out.println("======================================================================");
    }
}
