
package ec.edu.monster.crud_estudiante;

import datos.EstudianteRepositorio;
import logica.EstudianteControlador;
import presentacion.EstudianteVista;

public class CRUD_Estudiante {

    public static void main(String[] args) {

        EstudianteRepositorio repositorio =
                new EstudianteRepositorio();

        EstudianteVista vista = new EstudianteVista();

        EstudianteControlador controlador =
                new EstudianteControlador(repositorio, vista);

        controlador.iniciar();
    }
}
