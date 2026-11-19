package datos;

import componente.EstudianteComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Capa de datos para la persistencia en memoria de componentes de estudiantes
 * (tanto hojas como compuestos).
 */
public class EstudianteRepositorio {

    // Se utiliza LinkedHashMap para preservar el orden de inserción
    private final Map<String, EstudianteComponent> almacenamiento = new LinkedHashMap<>();

    public boolean guardar(EstudianteComponent componente) {
        if (componente == null || componente.getId() == null) {
            return false;
        }
        almacenamiento.put(componente.getId(), componente);
        return true;
    }

    public EstudianteComponent buscarPorId(String id) {
        if (id == null) {
            return null;
        }
        // Buscar directamente en la raíz
        EstudianteComponent encontrado = almacenamiento.get(id);
        if (encontrado != null) {
            return encontrado;
        }
        // Si no está en raíz, buscar recursivamente dentro de grupos compuestos
        for (EstudianteComponent raiz : almacenamiento.values()) {
            EstudianteComponent res = buscarRecursivo(raiz, id);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    private EstudianteComponent buscarRecursivo(EstudianteComponent actual, String id) {
        if (actual.getId().equalsIgnoreCase(id)) {
            return actual;
        }
        if (actual.esCompuesto()) {
            for (EstudianteComponent hijo : actual.obtenerHijos()) {
                EstudianteComponent res = buscarRecursivo(hijo, id);
                if (res != null) {
                    return res;
                }
            }
        }
        return null;
    }

    public boolean eliminar(String id) {
        if (id == null) {
            return false;
        }
        if (almacenamiento.containsKey(id)) {
            almacenamiento.remove(id);
            return true;
        }
        // Si es un hijo de algún grupo, retirarlo del grupo
        for (EstudianteComponent raiz : almacenamiento.values()) {
            if (raiz.esCompuesto()) {
                EstudianteComponent aEliminar = buscarRecursivo(raiz, id);
                if (aEliminar != null) {
                    eliminarDePadreRecursivo(raiz, aEliminar);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean eliminarDePadreRecursivo(EstudianteComponent padre, EstudianteComponent aEliminar) {
        if (padre.esCompuesto()) {
            if (padre.obtenerHijos().contains(aEliminar)) {
                padre.eliminar(aEliminar);
                return true;
            }
            for (EstudianteComponent hijo : padre.obtenerHijos()) {
                if (eliminarDePadreRecursivo(hijo, aEliminar)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<EstudianteComponent> listarRaiz() {
        return new ArrayList<>(almacenamiento.values());
    }

    public List<EstudianteComponent> listarTodosPlano() {
        List<EstudianteComponent> lista = new ArrayList<>();
        for (EstudianteComponent raiz : almacenamiento.values()) {
            aplanarRecursivo(raiz, lista);
        }
        return Collections.unmodifiableList(lista);
    }

    private void aplanarRecursivo(EstudianteComponent comp, List<EstudianteComponent> lista) {
        if (!lista.contains(comp)) {
            lista.add(comp);
        }
        if (comp.esCompuesto()) {
            for (EstudianteComponent hijo : comp.obtenerHijos()) {
                aplanarRecursivo(hijo, lista);
            }
        }
    }

    public boolean existe(String id) {
        return buscarPorId(id) != null;
    }
}
