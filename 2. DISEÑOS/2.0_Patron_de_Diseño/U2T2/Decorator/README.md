# CRUD estudiantes Decorator con interfaz gráfica Swing

## Cambios
Interfaz gráfica Swing. ID almacenado como `String` y validado con `L[0-9]{8}`: L mayúscula seguida de exactamente ocho dígitos, por ejemplo `L00423176`. Se conservan los ceros iniciales. Se ignoran únicamente espacios al principio y al final; se rechazan minúsculas, otra letra, longitud incorrecta y espacios internos.

## Arquitectura
| Capa | Paquete | Clases |
| --- | --- | --- |
| Presentación | presentacion | Main, CRUDEstudiantesGUI, Demo |
| Lógica | logica | OperacionesEstudiante, ControlEstudiante y patrones |
| Datos | datos | RepositorioEstudiante con Map<String, Estudiante> |
| Modelo | modelo | Estudiante con ID String, nombre String y edad int |

Utiliza Decorator para añadir auditoría al CRUD. Las reglas obligatorias permanecen en ControlEstudiante. La vista gráfica no accede al repositorio.

## Ejecutar
Requiere Java 17 o superior. Desde esta carpeta:
```powershell
java -jar CRUD_Estudiantes.jar
```
Para compilar con JDK 17 o superior y ejecutar, usar `ejecutar.bat` en Windows o `sh ejecutar.sh` en Linux/macOS. Comandos manuales en PowerShell:
```powershell
javac -encoding UTF-8 --release 17 -d out '@sources.txt'
java -cp out presentacion.Main
java -cp out pruebas.PruebaCrudEstudiantes
```
Se requiere un escritorio gráfico para abrir la aplicación. Para probar: `probar.bat`. Para demostración no interactiva: `java -cp out presentacion.Demo`.

## Interfaz gráfica
1. Registrar: completar ID (por ejemplo `L00423176`), Nombre y Edad, y pulsar Registrar.
2. Actualizar: seleccionar una fila, modificar Nombre o Edad y pulsar Actualizar. El ID permanece fijo.
3. Eliminar: seleccionar una fila y confirmar el diálogo. No se eliminan menores de 18 años.
4. Nuevo / Limpiar: vaciar campos y habilitar un nuevo registro.
5. Mostrar todos: recargar la tabla.
6. Auditoría: consultar las operaciones exitosas registradas por Decorator.

Los errores de validación se muestran en diálogos y no cierran la ventana. La tabla se actualiza después de cada operación exitosa. Cerrar la ventana termina la aplicación.

## Reglas y trazabilidad
| RF | Implementación | Validación |
| --- | --- | --- |
| RF-01 | agregarEstudiante | ID académico obligatorio y único; nombre y edad obligatorios |
| RF-02 | actualizarEstudiante | ID válido y existente; nombre y edad válidos |
| RF-03 | eliminarEstudiante | ID válido y existente; no se eliminan menores de 18 |
| RF-04 | listarTodos | Muestra los registros con sus IDs completos |
| RF-05 | validarId, validarDatos, enteroPositivo | Rechaza entradas inválidas sin modificar datos |

La edad es un entero positivo de 32 bits. No se aceptan nombres vacíos. La auditoría registra solo operaciones exitosas. Los datos se mantienen en memoria y se pierden al salir. No se implementan nuevas reglas académicas.

## Evidencias y comprobación visual
`evidencia_pruebas.txt`: pruebas de RF y patrones, incluyendo formato del ID. `evidencia_demo.txt`: demostración de la lógica (consola, sin interacción).
La aplicación Swing fue compilada. Este entorno no dispone de servidor gráfico; no se ejecutó la interacción visual. Comprobación manual: registrar L00423176 / Ana / 20, rechazar un duplicado y un ID incorrecto; seleccionar a Ana, actualizar su nombre; abrir auditoría y reporte cuando corresponda; cancelar y luego confirmar la eliminación. Verificar también el bloqueo al eliminar un menor.

Los diagramas editables y sus imágenes se encuentran en `diagramas/`.
