package ec.edu.monster.crud_estudiante;

import fachada.EstudianteFachada;
import java.awt.GraphicsEnvironment;
import javax.swing.SwingUtilities;
import logica.EstudianteControlador;
import presentacion.CRUDEstudiantesGUI;
import presentacion.EstudianteVista;
import presentacion.PruebaCrudEstudiantes;

/**
 * Punto de entrada principal de la aplicación CRUD Estudiantes.
 * Demuestra la flexibilidad del patrón Facade:
 * La misma Fachada (EstudianteFachada) abastece a la interfaz gráfica Swing (CRUDEstudiantesGUI),
 * al controlador interactivo de consola (EstudianteControlador) y a las pruebas automatizadas (PruebaCrudEstudiantes).
 */
public class CRUD_Estudiante {

    public static void main(String[] args) {
        EstudianteFachada fachada = new EstudianteFachada();

        if (args.length > 0 && args[0].equalsIgnoreCase("--prueba")) {
            PruebaCrudEstudiantes.main(args);
            return;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("--consola")) {
            EstudianteVista vista = new EstudianteVista();
            EstudianteControlador controlador = new EstudianteControlador(fachada, vista);
            controlador.iniciar();
            return;
        }

        // Si hay entorno gráfico disponible, abrir la GUI Swing; de lo contrario, consola
        if (!GraphicsEnvironment.isHeadless()) {
            SwingUtilities.invokeLater(() -> {
                CRUDEstudiantesGUI gui = new CRUDEstudiantesGUI(fachada);
                gui.setVisible(true);
            });
        } else {
            EstudianteVista vista = new EstudianteVista();
            EstudianteControlador controlador = new EstudianteControlador(fachada, vista);
            controlador.iniciar();
        }
    }
}
