# CRUD de Estudiantes en Java — Arquitectura en Capas y Patrón Composite

## Propósito
Diseñar e implementar un CRUD orientado a objetos para estudiantes aplicando el patrón de diseño estructural **Composite** sobre una arquitectura de capas (Presentación, Lógica de Negocio, Datos y Componentes del Dominio).

El patrón **Composite** permite componer objetos en estructuras de árbol para representar jerarquías de parte-todo. Gracias a este patrón, los clientes (como la lógica de negocio y la interfaz gráfica) pueden tratar de manera uniforme tanto a estudiantes individuales (nodos Hoja / Leaf) como a agrupaciones de estudiantes (nodos Compuestos / Composite, como cursos, paralelos o equipos de trabajo).

## Requisitos Funcionales Implementados

| Código | Requisito Funcional ERS/SRS | Operación CRUD | Componentes Implementados |
| --- | --- | --- | --- |
| RF-01 | Permitir registrar un nuevo estudiante con ID, Nombre y Edad, y crear grupos compuestos. | Create | `EstudianteService.registrarEstudiante`, `EstudianteService.registrarGrupo`, `EstudianteRepositorio.guardar`, `Estudiante`, `GrupoEstudiantes` |
| RF-02 | Permitir actualizar los datos de un estudiante o grupo existente. | Update | `EstudianteService.actualizarEstudiante`, `Estudiante`, `GrupoEstudiantes` |
| RF-03 | Permitir eliminar un estudiante o grupo aplicando la regla académica institucional (no eliminar menores de 18 años). | Delete | `EstudianteService.eliminar`, `EstudianteRepositorio.eliminar` |
| RF-04 | Mostrar la estructura completa de estudiantes y grupos tanto en formato plano como en árbol jerárquico. | Read / List | `EstudianteService.listarTodos`, `EstudianteService.obtenerArbolCompleto`, `CRUDEstudiantesGUI`, `PruebaCrudEstudiantes` |
| RF-05 | Validar campos obligatorios antes de realizar cualquier operación (ID no vacío, Nombre válido, Edad positiva). | Validación | `EstudianteService.validarDatosEstudiante` |

## Estructura del Proyecto

```text
CRUD_Esttudante_Composite/
│   pom.xml
│   README.md
│   diagrama_composite_crud_estudiante.puml
└───src/main/java/
    ├───componente/
    │       EstudianteComponent.java        // Componente base abstracto
    ├───hoja/
    │       Estudiante.java                 // Hoja (Leaf - estudiante individual)
    ├───compuesto/
    │       GrupoEstudiantes.java           // Compuesto (Composite - grupo de estudiantes)
    ├───datos/
    │       EstudianteRepositorio.java      // Repositorio en memoria con soporte jerárquico
    ├───logica/
    │       EstudianteService.java          // Lógica de negocio, validaciones y reglas
    │       ResultadoOperacion.java         // Objeto de transferencia de resultado
    └───presentacion/
            CRUDEstudiantesGUI.java         // Interfaz gráfica Swing con JTree y JTable
            PruebaCrudEstudiantes.java      // Suite de pruebas por consola
```

## Ejecución

### Compilación:
```powershell
javac -encoding UTF-8 -d target/classes (Get-ChildItem -Path src/main/java -Filter *.java -Recurse | Select-Object -ExpandProperty FullName)
```

### Ejecutar Pruebas Automatizadas de Consola:
```powershell
java -cp target/classes presentacion.PruebaCrudEstudiantes
```

### Ejecutar Interfaz Gráfica Swing:
```powershell
java -cp target/classes presentacion.CRUDEstudiantesGUI
```
