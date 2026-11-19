package presentacion;
import java.awt.*;
import java.util.function.Supplier;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logica.OperacionesEstudiante;
import modelo.Estudiante;

/** Vista Swing: llama a la lógica; nunca al repositorio. */
public final class CRUDEstudiantesGUI extends JFrame {
    private final OperacionesEstudiante servicio;
    private final JTextField id = new JTextField(10), nombre = new JTextField(22), edad = new JTextField(6);
    private final DefaultTableModel filas = new DefaultTableModel(new String[]{"ID", "Nombre", "Edad"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(filas);
    private final JButton agregar = new JButton("Registrar"), actualizar = new JButton("Actualizar"), eliminar = new JButton("Eliminar");
    private final JLabel estado = new JLabel();
    public CRUDEstudiantesGUI(OperacionesEstudiante servicio, String titulo, Supplier<String> reporte, Supplier<String> auditoria) {
        super(titulo); this.servicio = servicio;
        setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(1000, 560); setMinimumSize(new Dimension(960, 480));
        JPanel principal = new JPanel(new BorderLayout(12, 12));
        principal.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18)); setContentPane(principal);
        JPanel superior = new JPanel(new GridLayout(2, 1, 8, 8));
        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        id.setToolTipText("L mayúscula y ocho dígitos. Ejemplo: L00423176");
        formulario.add(new JLabel("ID (L + 8 dígitos)")); formulario.add(id); formulario.add(new JLabel("Nombre")); formulario.add(nombre);
        formulario.add(new JLabel("Edad")); formulario.add(edad); superior.add(formulario);
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(agregar); botones.add(actualizar); botones.add(eliminar);
        JButton nuevo = new JButton("Nuevo / Limpiar"), listar = new JButton("Mostrar todos");
        botones.add(nuevo); botones.add(listar);
        if (reporte != null) { JButton b = new JButton("Reporte"); b.addActionListener(e -> mostrar("Reporte", reporte.get())); botones.add(b); }
        if (auditoria != null) { JButton b = new JButton("Auditoría"); b.addActionListener(e -> mostrar("Auditoría", auditoria.get())); botones.add(b); }
        superior.add(botones); principal.add(superior, BorderLayout.NORTH);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); tabla.setRowHeight(25);
        principal.add(new JScrollPane(tabla), BorderLayout.CENTER);
        principal.add(estado, BorderLayout.SOUTH);
        agregar.addActionListener(e -> ejecutar(() -> servicio.agregarEstudiante(id.getText(), nombre.getText(), edad.getText())));
        actualizar.addActionListener(e -> { if (tabla.getSelectedRow() >= 0) ejecutar(() -> servicio.actualizarEstudiante(id.getText(), nombre.getText(), edad.getText())); });
        eliminar.addActionListener(e -> {
            if (tabla.getSelectedRow() >= 0 && JOptionPane.showConfirmDialog(this, "¿Eliminar el ID " + id.getText() + "?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                ejecutar(() -> servicio.eliminarEstudiante(id.getText()));
        });
        nuevo.addActionListener(e -> limpiar()); listar.addActionListener(e -> refrescar());
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (!e.getValueIsAdjusting() && fila >= 0) {
                id.setText(filas.getValueAt(fila, 0).toString()); nombre.setText(filas.getValueAt(fila, 1).toString()); edad.setText(filas.getValueAt(fila, 2).toString());
                id.setEditable(false); agregar.setEnabled(false); actualizar.setEnabled(true); eliminar.setEnabled(true);
            }
        });
        refrescar(); setLocationRelativeTo(null);
    }
    private void limpiar() {
        tabla.clearSelection(); id.setText(""); nombre.setText(""); edad.setText(""); id.setEditable(true);
        agregar.setEnabled(true); actualizar.setEnabled(false); eliminar.setEnabled(false); id.requestFocusInWindow();
    }
    private void refrescar() {
        limpiar(); filas.setRowCount(0);
        for (Estudiante e : servicio.listarTodos()) filas.addRow(new Object[]{e.getId(), e.getNombre(), e.getEdad()});
        estado.setText("Total: " + filas.getRowCount() + " | Datos en memoria | No se eliminan menores de 18 años");
    }
    private void ejecutar(Runnable accion) {
        try { accion.run(); refrescar(); JOptionPane.showMessageDialog(this, "Operación completada."); }
        catch (IllegalArgumentException e) { JOptionPane.showMessageDialog(this, e.getMessage(), "Validación", JOptionPane.ERROR_MESSAGE); }
    }
    private void mostrar(String titulo, String contenido) {
        JTextArea area = new JTextArea(contenido, 18, 65); area.setEditable(false); area.setCaretPosition(0);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}
