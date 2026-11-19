package presentacion;

import fachada.EstudianteFachada;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import logica.ResultadoOperacion;
import modelo.Estudiante;

/**
 * Capa de Presentación: Interfaz Gráfica de Usuario (GUI) Swing.
 * Interactúa EXCLUSIVAMENTE con EstudianteFachada para todas las operaciones del CRUD (RF-01 a RF-05).
 * No accede directamente al repositorio ni a persistencia.
 */
public class CRUDEstudiantesGUI extends JFrame {

    private final EstudianteFachada fachada;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JTable tablaEstudiantes;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstado;

    public CRUDEstudiantesGUI() {
        this(new EstudianteFachada());
    }

    public CRUDEstudiantesGUI(EstudianteFachada fachada) {
        this.fachada = fachada;
        inicializarComponentes();
        actualizarLista();
    }

    private void inicializarComponentes() {
        setTitle("CRUD Estudiantes - Arquitectura en Capas con Patrón Facade");
        setSize(750, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Encabezado
        JLabel lblTitulo = new JLabel("Sistema de Gestión de Estudiantes (Patrón Fachada)", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel de Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("ID / Matrícula:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        panelFormulario.add(txtId, gbc);

        // Fila 1: Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);

        // Fila 2: Edad
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 1;
        txtEdad = new JTextField(10);
        panelFormulario.add(txtEdad, gbc);

        // Panel de Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRegistrar = new JButton("Registrar (RF-01)");
        JButton btnActualizar = new JButton("Actualizar (RF-02)");
        JButton btnEliminar = new JButton("Eliminar (RF-03)");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnRegistrar.addActionListener(e -> onAgregar());
        btnActualizar.addActionListener(e -> onActualizar());
        btnEliminar.addActionListener(e -> onEliminar());
        btnBuscar.addActionListener(e -> onBuscar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal.add(panelSuperior, BorderLayout.WEST);

        // Tabla de datos (RF-04)
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Edad"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEstudiantes.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = tablaEstudiantes.getSelectedRow();
            if (selectedRow >= 0) {
                txtId.setText(modeloTabla.getValueAt(selectedRow, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(selectedRow, 1).toString());
                txtEdad.setText(modeloTabla.getValueAt(selectedRow, 2).toString());
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaEstudiantes);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Estudiantes Registrados (RF-04)"));
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // Barra de estado inferior
        lblEstado = new JLabel("Listo. Seleccione una operación.", JLabel.LEFT);
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEstado.setBorder(new EmptyBorder(5, 5, 5, 5));
        panelPrincipal.add(lblEstado, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    // RF-01: Registrar
    public void onAgregar() {
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String edadStr = txtEdad.getText().trim();

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: la edad debe ser un número entero válido.", true);
            return;
        }

        // Llamada a la Fachada
        ResultadoOperacion res = fachada.registrar(id, nombre, edad);
        if (res.isExitoso()) {
            mostrarMensaje(res.getMensaje(), false);
            actualizarLista();
            limpiarFormulario();
        } else {
            mostrarMensaje(res.getMensaje(), true);
        }
    }

    // RF-02: Actualizar
    public void onActualizar() {
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String edadStr = txtEdad.getText().trim();

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: la edad debe ser un número entero válido.", true);
            return;
        }

        // Llamada a la Fachada
        ResultadoOperacion res = fachada.actualizar(id, nombre, edad);
        if (res.isExitoso()) {
            mostrarMensaje(res.getMensaje(), false);
            actualizarLista();
        } else {
            mostrarMensaje(res.getMensaje(), true);
        }
    }

    // RF-03: Eliminar
    public void onEliminar() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            mostrarMensaje("Error: seleccione o ingrese el ID a eliminar.", true);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el estudiante con ID " + id + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        // Llamada a la Fachada
        ResultadoOperacion res = fachada.eliminar(id);
        if (res.isExitoso()) {
            mostrarMensaje(res.getMensaje(), false);
            actualizarLista();
            limpiarFormulario();
        } else {
            // Muestra error (por ejemplo, si la regla académica bloquea menores de 18 años)
            mostrarMensaje(res.getMensaje(), true);
        }
    }

    // Buscar por ID
    public void onBuscar() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            mostrarMensaje("Error: ingrese el ID que desea buscar.", true);
            return;
        }

        Estudiante e = fachada.buscarPorId(id);
        if (e != null) {
            txtNombre.setText(e.getNombre());
            txtEdad.setText(String.valueOf(e.getEdad()));
            mostrarMensaje("Estudiante encontrado: " + e.getNombre(), false);
        } else {
            mostrarMensaje("Estudiante no encontrado con ID: " + id, true);
        }
    }

    // RF-04: Actualizar lista de estudiantes en tabla
    public void actualizarLista() {
        modeloTabla.setRowCount(0);
        List<Estudiante> lista = fachada.listarTodos();
        for (Estudiante e : lista) {
            modeloTabla.addRow(new Object[]{e.getId(), e.getNombre(), e.getEdad()});
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        tablaEstudiantes.clearSelection();
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        lblEstado.setText(mensaje);
        lblEstado.setForeground(esError ? new Color(180, 0, 0) : new Color(0, 120, 0));
        if (esError) {
            JOptionPane.showMessageDialog(this, mensaje, "Aviso del Sistema", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CRUDEstudiantesGUI gui = new CRUDEstudiantesGUI();
            gui.setVisible(true);
        });
    }
}
