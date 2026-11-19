package presentacion;

import componente.EstudianteComponent;
import logica.EstudianteService;
import logica.ResultadoOperacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Interfaz Gráfica de Usuario (GUI) Swing para el CRUD de Estudiantes con Patrón Composite.
 * Permite gestionar estudiantes individuales y agrupaciones compuestas en un árbol y tabla interactiva.
 */
public class CRUDEstudiantesGUI extends JFrame {

    private final EstudianteService servicio;

    // Componentes del formulario
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JComboBox<String> cmbTipoComponente;
    private JComboBox<String> cmbGrupoDestino;

    // Tabla y Árbol
    private JTable tablaComponentes;
    private DefaultTableModel modeloTabla;
    private JTree arbolComponentes;
    private DefaultTreeModel modeloArbol;
    private DefaultMutableTreeNode nodoRaiz;

    // Etiquetas de estadísticas compuestas
    private JLabel lblTotalEstudiantes;
    private JLabel lblPromedioEdad;
    private JLabel lblEstado;

    public CRUDEstudiantesGUI() {
        this.servicio = new EstudianteService();
        inicializarComponentes();
        cargarDatosDemo();
        actualizarVistas();
    }

    private void inicializarComponentes() {
        setTitle("CRUD de Estudiantes - Patrón Estructural Composite (ADSW)");
        setSize(980, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // Panel Superior: Título
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(24, 60, 110));
        panelTitulo.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN DE ESTUDIANTES - PATRÓN COMPOSITE");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo, BorderLayout.WEST);

        JLabel lblSubtitulo = new JLabel("Ingeniería de Software - ADSW");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(210, 230, 255));
        panelTitulo.add(lblSubtitulo, BorderLayout.EAST);

        add(panelTitulo, BorderLayout.NORTH);

        // Panel Izquierdo: Formulario y Acciones
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(320, 0));
        panelIzquierdo.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 6, 8));
        panelForm.setBorder(new TitledBorder("Datos del Componente"));

        panelForm.add(new JLabel("Tipo:"));
        cmbTipoComponente = new JComboBox<>(new String[]{"Estudiante (Hoja)", "Grupo (Compuesto)"});
        panelForm.add(cmbTipoComponente);

        panelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Edad (años):"));
        txtEdad = new JTextField();
        panelForm.add(txtEdad);

        panelForm.add(new JLabel("Asignar a Grupo:"));
        cmbGrupoDestino = new JComboBox<>();
        panelForm.add(cmbGrupoDestino);

        cmbTipoComponente.addActionListener(e -> {
            boolean esGrupo = cmbTipoComponente.getSelectedIndex() == 1;
            txtEdad.setEnabled(!esGrupo);
            if (esGrupo) {
                txtEdad.setText("Calculado aut.");
            } else {
                txtEdad.setText("");
            }
        });

        panelIzquierdo.add(panelForm);
        panelIzquierdo.add(Box.createVerticalStrut(10));

        // Botones de Acción
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 6, 6));
        panelBotones.setBorder(new TitledBorder("Operaciones"));

        JButton btnRegistrar = new JButton("1. Registrar / Crear");
        JButton btnAgregarAGrupo = new JButton("2. Asignar al Grupo Seleccionado");
        JButton btnActualizar = new JButton("3. Actualizar Datos");
        JButton btnEliminar = new JButton("4. Eliminar (Regla Académica)");
        JButton btnLimpiar = new JButton("5. Limpiar Formulario");

        btnRegistrar.addActionListener(e -> onRegistrar());
        btnAgregarAGrupo.addActionListener(e -> onAsignarAGrupo());
        btnActualizar.addActionListener(e -> onActualizar());
        btnEliminar.addActionListener(e -> onEliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnAgregarAGrupo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        panelIzquierdo.add(panelBotones);
        panelIzquierdo.add(Box.createVerticalStrut(10));

        // Panel Estadísticas Compuestas
        JPanel panelStats = new JPanel(new GridLayout(2, 1, 4, 4));
        panelStats.setBorder(new TitledBorder("Estadísticas Globales (Composite)"));
        lblTotalEstudiantes = new JLabel("Total estudiantes: 0");
        lblPromedioEdad = new JLabel("Promedio edad global: 0.0 años");
        lblTotalEstudiantes.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPromedioEdad.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelStats.add(lblTotalEstudiantes);
        panelStats.add(lblPromedioEdad);

        panelIzquierdo.add(panelStats);
        add(panelIzquierdo, BorderLayout.WEST);

        // Panel Central: Split de Árbol y Tabla
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.4);

        // Árbol Jerárquico
        nodoRaiz = new DefaultMutableTreeNode("Instituto / Universidad (Raíz)");
        modeloArbol = new DefaultTreeModel(nodoRaiz);
        arbolComponentes = new JTree(modeloArbol);
        arbolComponentes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane scrollArbol = new JScrollPane(arbolComponentes);
        scrollArbol.setBorder(new TitledBorder("Vista Jerárquica del Patrón Composite"));
        splitPane.setLeftComponent(scrollArbol);

        // Tabla plana de registros
        String[] columnas = {"ID", "Tipo", "Nombre / Grupo", "Edad / Prom.", "Sub-elementos"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaComponentes = new JTable(modeloTabla);
        tablaComponentes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaComponentes.setRowHeight(22);
        JScrollPane scrollTabla = new JScrollPane(tablaComponentes);
        scrollTabla.setBorder(new TitledBorder("Listado de Componentes"));
        splitPane.setRightComponent(scrollTabla);

        add(splitPane, BorderLayout.CENTER);

        // Barra de Estado Inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(new EmptyBorder(4, 8, 4, 8));
        lblEstado = new JLabel("Listo.");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelInferior.add(lblEstado, BorderLayout.WEST);
        add(panelInferior, BorderLayout.SOUTH);

        // Evento de selección en tabla
        tablaComponentes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaComponentes.getSelectedRow();
                if (fila >= 0) {
                    String id = modeloTabla.getValueAt(fila, 0).toString();
                    EstudianteComponent comp = servicio.consultarPorId(id);
                    if (comp != null) {
                        txtId.setText(comp.getId());
                        txtId.setEnabled(false); // ID no editable durante actualización
                        txtNombre.setText(comp.getNombre());
                        if (comp.esCompuesto()) {
                            cmbTipoComponente.setSelectedIndex(1);
                            txtEdad.setText(String.format("%.1f", comp.obtenerPromedioEdad()));
                        } else {
                            cmbTipoComponente.setSelectedIndex(0);
                            txtEdad.setText(String.valueOf(comp.getEdad()));
                        }
                    }
                }
            }
        });
    }

    private void cargarDatosDemo() {
        servicio.registrarEstudiante("1", "Ana Torres", 20);
        servicio.registrarEstudiante("2", "Luis Perez", 17);
        servicio.registrarEstudiante("3", "Carlos Andrade", 22);
        servicio.registrarEstudiante("4", "Maria Beltran", 19);

        servicio.registrarGrupo("G-01", "Paralelo A - Software");
        servicio.registrarGrupo("G-EQ1", "Equipo Desarrollo Web");

        servicio.agregarAGrupo("G-EQ1", "3");
        servicio.agregarAGrupo("G-EQ1", "4");

        servicio.agregarAGrupo("G-01", "1");
        servicio.agregarAGrupo("G-01", "2");
        servicio.agregarAGrupo("G-01", "G-EQ1");
    }

    private void onRegistrar() {
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        boolean esGrupo = cmbTipoComponente.getSelectedIndex() == 1;

        ResultadoOperacion res;
        if (esGrupo) {
            res = servicio.registrarGrupo(id, nombre);
        } else {
            int edad;
            try {
                edad = Integer.parseInt(txtEdad.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un número entero válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            res = servicio.registrarEstudiante(id, nombre, edad);
        }

        if (res.isExitoso()) {
            lblEstado.setText(res.getMensaje());
            actualizarVistas();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, res.getMensaje(), "Validación / Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onAsignarAGrupo() {
        String idElemento = txtId.getText().trim();
        String seleccionGrupo = (String) cmbGrupoDestino.getSelectedItem();

        if (idElemento.isEmpty() || seleccionGrupo == null || seleccionGrupo.startsWith("--")) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento del formulario y un grupo destino.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String idGrupo = seleccionGrupo.split(" - ")[0].trim();
        ResultadoOperacion res = servicio.agregarAGrupo(idGrupo, idElemento);
        if (res.isExitoso()) {
            lblEstado.setText(res.getMensaje());
            actualizarVistas();
        } else {
            JOptionPane.showMessageDialog(this, res.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onActualizar() {
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        boolean esGrupo = cmbTipoComponente.getSelectedIndex() == 1;

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para actualizar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ResultadoOperacion res;
        if (esGrupo) {
            res = servicio.actualizarEstudiante(id, nombre, 0);
        } else {
            int edad;
            try {
                edad = Integer.parseInt(txtEdad.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un número entero válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            res = servicio.actualizarEstudiante(id, nombre, edad);
        }

        if (res.isExitoso()) {
            lblEstado.setText(res.getMensaje());
            actualizarVistas();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, res.getMensaje(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onEliminar() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione o ingrese el ID del elemento a eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el elemento con ID: " + id + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        ResultadoOperacion res = servicio.eliminar(id);
        if (res.isExitoso()) {
            lblEstado.setText(res.getMensaje());
            actualizarVistas();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, res.getMensaje(), "Regla Académica / Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtId.setEnabled(true);
        txtNombre.setText("");
        txtEdad.setText("");
        cmbTipoComponente.setSelectedIndex(0);
        txtEdad.setEnabled(true);
        tablaComponentes.clearSelection();
    }

    private void actualizarVistas() {
        // Actualizar Tabla
        modeloTabla.setRowCount(0);
        List<EstudianteComponent> todos = servicio.listarTodos();
        for (EstudianteComponent c : todos) {
            String tipo = c.esCompuesto() ? "Grupo (Composite)" : "Estudiante (Hoja)";
            String edadStr = c.esCompuesto() ? String.format("%.1f", c.obtenerPromedioEdad()) : String.valueOf(c.getEdad());
            String hijosStr = c.esCompuesto() ? (c.obtenerHijos().size() + " elementos directos") : "0 (Hoja)";
            modeloTabla.addRow(new Object[]{c.getId(), tipo, c.getNombre(), edadStr, hijosStr});
        }

        // Actualizar Combo de Grupos
        cmbGrupoDestino.removeAllItems();
        cmbGrupoDestino.addItem("-- Seleccionar Grupo --");
        for (EstudianteComponent c : todos) {
            if (c.esCompuesto()) {
                cmbGrupoDestino.addItem(c.getId() + " - " + c.getNombre());
            }
        }

        // Actualizar Árbol JTree
        nodoRaiz.removeAllChildren();
        for (EstudianteComponent raiz : servicio.listarRaiz()) {
            construirNodoArbolRecursivo(nodoRaiz, raiz);
        }
        modeloArbol.reload();
        for (int i = 0; i < arbolComponentes.getRowCount(); i++) {
            arbolComponentes.expandRow(i);
        }

        // Actualizar Estadísticas
        int totalEstudiantes = 0;
        double sumaPonderada = 0;
        for (EstudianteComponent c : todos) {
            if (!c.esCompuesto()) {
                totalEstudiantes++;
                sumaPonderada += c.getEdad();
            }
        }
        lblTotalEstudiantes.setText("Total estudiantes registrados: " + totalEstudiantes);
        double promedio = totalEstudiantes > 0 ? (sumaPonderada / totalEstudiantes) : 0.0;
        lblPromedioEdad.setText(String.format("Promedio de edad global: %.2f años", promedio));
    }

    private void construirNodoArbolRecursivo(DefaultMutableTreeNode nodoPadre, EstudianteComponent comp) {
        String textoNodo = comp.esCompuesto()
                ? "📁 [Grupo: " + comp.getId() + "] " + comp.getNombre() + " (" + comp.contarEstudiantes() + " alumnos, prom: " + String.format("%.1f", comp.obtenerPromedioEdad()) + " años)"
                : "👤 [ID: " + comp.getId() + "] " + comp.getNombre() + " (" + comp.getEdad() + " años)";
        DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(textoNodo);
        nodoPadre.add(nuevoNodo);

        if (comp.esCompuesto()) {
            for (EstudianteComponent hijo : comp.obtenerHijos()) {
                construirNodoArbolRecursivo(nuevoNodo, hijo);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new CRUDEstudiantesGUI().setVisible(true);
        });
    }
}
