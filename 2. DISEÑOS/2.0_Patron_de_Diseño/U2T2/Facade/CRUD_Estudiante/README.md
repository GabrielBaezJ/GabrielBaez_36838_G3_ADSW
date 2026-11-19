# U2T1 - CRUD Estudiante en arquitectura de tres capas con Patron Facade

## Proposito

Disenar e implementar un CRUD base para estudiantes aplicando el patron de diseno estructural Facade (Fachada) sobre una arquitectura de tres capas (Presentacion, Logica de negocio y Datos, junto con el Modelo). 

La fachada (`EstudianteFachada`) actua como interfaz unificada y simplificada para la capa de presentacion, encapsulando y coordinando las validaciones de campos obligatorios, la evaluacion de reglas academicas y las operaciones de persistencia en memoria. El CRUD permite registrar, consultar, actualizar y eliminar estudiantes con los campos `ID`, `Nombre` y `Edad`.

## Productos verificables

| Producto | Estado | Evidencia |
| --- | --- | --- |
| Proyecto base organizado en capas con Patron Facade | Cumplido | Paquetes `presentacion`, `fachada`, `logica`, `datos` y `modelo`. |
| Diagrama o esquema de arquitectura | Cumplido | Archivos `diagrama_fachada_crud_estudiante.puml` y `diagrama_tres_capas_crud_estudiante.puml`. |
| Tabla de responsabilidades por capa | Cumplido | Seccion "Responsabilidades por capa". |
| Matriz de trazabilidad | Cumplido | Seccion "Matriz de trazabilidad sugerida". |
| Pruebas de registro, consulta, actualizacion y eliminacion | Cumplido | Clases `presentacion.PruebaCrudEstudiantes` y `presentacion.CRUDEstudiantesGUI`. |

## Responsabilidades por capa

| Capa | Paquete | Responsabilidad | Clases |
| --- | --- | --- | --- |
| Presentacion | `presentacion` | Capturar datos del usuario, invocar exclusivamente la Fachada y mostrar resultados en GUI o consola. No accede a persistencia. | `CRUDEstudiantesGUI`, `PruebaCrudEstudiantes` |
| Fachada | `fachada` | Proporcionar una interfaz unificada y simplificada para la capa de presentacion, orquestando el repositorio, validador y reglas academicas. | `EstudianteFachada` |
| Logica de negocio | `logica` | Validar datos obligatorios (RF-05), evaluar reglas academicas institucionales (RF-03) y encapsular respuestas. | `ValidadorEstudiante`, `ReglaAcademicaServicio`, `ResultadoOperacion`, `ControlEstudiante` |
| Datos | `datos` | Gestionar la coleccion de estudiantes en memoria sin reglas de negocio. | `EstudianteRepositorio`, `RepositorioEstudiante` |
| Modelo | `modelo` | Representar la entidad del dominio con ID, Nombre y Edad. | `Estudiante` |

## Matriz de trazabilidad sugerida

Esta matriz relaciona cada requisito funcional de la ERS/SRS con el caso de uso, la operacion del CRUD y los componentes implementados.

| Codigo | Requisito funcional ERS/SRS | Caso de uso | Operacion CRUD | Componentes implementados | Evidencia de prueba |
| --- | --- | --- | --- | --- | --- |
| RF-01 | El sistema debe permitir registrar un nuevo estudiante con ID, Nombre y Edad. | Agregar estudiante | Create | `CRUDEstudiantesGUI.onAgregar`, `EstudianteFachada.registrar`, `EstudianteRepositorio.guardar`, `Estudiante` | `PruebaCrudEstudiantes`: bloque "RF-01 Registrar" |
| RF-02 | El sistema debe permitir actualizar los datos de un estudiante existente usando ID, Nombre y Edad. | Actualizar estudiante | Update | `CRUDEstudiantesGUI.onActualizar`, `EstudianteFachada.actualizar`, `EstudianteRepositorio.actualizar` | `PruebaCrudEstudiantes`: bloque "RF-02 Actualizar" |
| RF-03 | El sistema debe permitir eliminar un estudiante seleccionado cuando corresponda segun la regla academica definida (no eliminar menores de 18 anios). | Eliminar estudiante | Delete | `CRUDEstudiantesGUI.onEliminar`, `EstudianteFachada.eliminar`, `ReglaAcademicaServicio.puedeEliminar`, `EstudianteRepositorio.eliminar` | `PruebaCrudEstudiantes`: bloque "RF-03 Eliminar" |
| RF-04 | El sistema debe permitir mostrar todos los estudiantes registrados con ID, Nombre y Edad. | Mostrar estudiantes | Read/List | `CRUDEstudiantesGUI.actualizarLista`, `EstudianteFachada.listarTodos`, `EstudianteRepositorio.listarTodos` | `PruebaCrudEstudiantes`: bloque "RF-04 Consultar" |
| RF-05 | El sistema debe validar datos obligatorios antes de guardar o actualizar cambios. | Validar datos del estudiante | Validacion | `EstudianteFachada.validarDatos`, `ValidadorEstudiante.validarDatos` | Mensajes de error ante ID vacio, Nombre con caracteres invalidos o Edad fuera de rango |

## Matriz de trazabilidad global integrada

| Requisito / entrada | U2T1 Arquitectura | U2T2 Patrones estructurales (Facade) | U2T3 Patrones de comportamiento | Evidencia final | Control de coherencia |
| --- | --- | --- | --- | --- | --- |
| RF priorizados del CRUD | Se implementan operaciones base del estudiante: registrar, consultar, actualizar y eliminar. | Se implementa `EstudianteFachada` unificando subsistemas de persistencia, validacion y reglas. | Se agregan eventos del CRUD y manejo de respuestas encapsuladas. | Codigo, diagramas PlantUML y pruebas de consola / GUI. | Ningun patron debe aparecer sin requisito asociado. |
| RNF de mantenibilidad | Se separan responsabilidades por capas. | Se reduce el acoplamiento directo entre la presentacion y los subsistemas con Facade. | Se facilita el cambio de comportamiento y reglas de negocio sin afectar la presentacion. | Justificacion tecnica en este README. | La solucion debe ser mas flexible, no mas compleja innecesariamente. |
| Arquitectura | Se definen Presentacion, Logica de negocio, Datos y Modelo. | La Fachada se inserta como punto de acceso unico respetando las capas. | Los patrones de comportamiento se ubican principalmente en la logica de negocio. | Esquemas PlantUML actualizados. | La presentacion no debe asumir responsabilidades de datos o reglas complejas. |
| Diseno orientado a objetos | Se identifican clases, metodos y responsabilidades. | Se aplica el patron estructural Facade para simplificar el uso del subsistema. | Se aplican validaciones y evaluacion de reglas de negocio desacopladas. | Diagramas de clases/componentes. | Los diagramas deben coincidir con la implementacion. |
| Evidencias de funcionamiento | Se ejecuta el CRUD base. | Se demuestra la coordinacion de subsistemas mediante la Fachada. | Se demuestra el bloqueo ante la regla academica de menores de edad. | Capturas, interfaz grafica y pruebas de consola. | Cada evidencia debe vincularse a una tarea y requisito. |

## Diagrama PlantUML

Los diagramas actualizados estan en:

```text
diagrama_fachada_crud_estudiante.puml
diagrama_tres_capas_crud_estudiante.puml
```

Resumen del flujo:

```text
Presentacion -> Fachada -> Subsistemas (Validador, ReglaAcademica, Repositorio) -> Modelo
CRUDEstudiantesGUI / PruebaCrudEstudiantes -> EstudianteFachada -> (ValidadorEstudiante, ReglaAcademicaServicio, EstudianteRepositorio) -> Estudiante
```

## Criterios de aceptacion

| Criterio | Cumplimiento |
| --- | --- |
| La capa de presentacion no accede directamente a la persistencia. | Cumplido: `CRUDEstudiantesGUI` y `PruebaCrudEstudiantes` solo usan `EstudianteFachada`. |
| Las validaciones del estudiante estan en la logica de negocio. | Cumplido: `validarDatos` esta implementado en `ValidadorEstudiante`. |
| Se aplica la regla academica de eliminacion. | Cumplido: `ReglaAcademicaServicio` restringe eliminar menores de 18 anios (RF-03). |
| Las operaciones CRUD estan vinculadas a requisitos de la ERS/SRS. | Cumplido: ver matriz RF-01 a RF-05. |
| El proyecto es comprensible, ejecutable y documentado. | Cumplido: README, PlantUML, GUI Swing y prueba de consola. |

## Como ejecutar

Desde PowerShell, ubicarse en la carpeta del proyecto:

```powershell
cd "C:\Users\vanne\Documents\6TO SEMESTRE\ANALISIS Y DISEÑOS\UNIDAD 2\CODIGOS\CRUD_Estudiante"
```

Compilar:

```powershell
mvn compile
```

Ejecutar prueba de consola:

```powershell
java -cp target/classes presentacion.PruebaCrudEstudiantes
```

Ejecutar interfaz grafica:

```powershell
java -cp target/classes presentacion.CRUDEstudiantesGUI
```

## Evidencia esperada de consola

```text
=== RF-01 Registrar ===
Agregado exitosamente: Ana Torres
Agregado exitosamente: Luis Perez

=== RF-04 Consultar ===
Estudiante [ID=1, Nombre=Ana Torres, Edad=20]
Estudiante [ID=2, Nombre=Luis Perez, Edad=17]

=== RF-02 Actualizar ===
Actualizado exitosamente: ID 1

=== RF-03 Eliminar ===
Error: Regla academica - no se puede eliminar a 'Luis Perez' porque es menor de 18 anios (Edad: 17).
Eliminado exitosamente: Ana Torres Actualizada (ID 1)

=== Lista final ===
Estudiante [ID=2, Nombre=Luis Perez, Edad=17]
```
