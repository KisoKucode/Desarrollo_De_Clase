import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppClinica extends JFrame {
    private List<Paciente> pacientes = new ArrayList<>();
    private int contadorTurno;
    private DefaultTableModel modeloTabla;

    public AppClinica() {
        // 1. Configuración Inicial
        String inicio = JOptionPane.showInputDialog("Indicador de inicio (ej: 100):");
        this.contadorTurno = (inicio != null) ? Integer.parseInt(inicio) : 1;

        setTitle("Sistema de Turnos Clínica - Operaria");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 2. Panel de Registro (Norte)
        JPanel panelRegistro = new JPanel(new FlowLayout());
        JTextField txtId = new JTextField(10);
        JComboBox<TipoPaciente> comboTipo = new JComboBox<>(TipoPaciente.values());
        JButton btnAdd = new JButton("Generar Ticket");
        
        panelRegistro.add(new JLabel("ID Paciente:"));
        panelRegistro.add(txtId);
        panelRegistro.add(new JLabel("Tipo:"));
        panelRegistro.add(comboTipo);
        panelRegistro.add(btnAdd);

        // 3. Tabla de la Operaria (Centro)
        String[] columnas = {"Ticket", "ID Identificación", "Hora Solicitud"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // 4. Panel de Acciones (Sur)
        JPanel panelAcciones = new JPanel();
        JButton btnAtender = new JButton("Atender Siguiente");
        JButton btnCancelar = new JButton("Cancelar Turno Seleccionado");
        panelAcciones.add(btnAtender);
        panelAcciones.add(btnCancelar);

        // --- LÓGICA DE EVENTOS ---

        btnAdd.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                Paciente p = new Paciente(txtId.getText(), (TipoPaciente)comboTipo.getSelectedItem(), contadorTurno++);
                pacientes.add(p);
                actualizarTabla();
                txtId.setText("");
            }
        });

        btnAtender.addActionListener(e -> {
            for (Paciente p : pacientes) {
                if (!p.isAtendido()) {
                    p.setAtendido(true);
                    JOptionPane.showMessageDialog(this, "Llamando al turno: " + p.getTicket());
                    actualizarTabla();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "No hay más pacientes.");
        });

        btnCancelar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                String ticketBorrar = (String) modeloTabla.getValueAt(fila, 0);
                pacientes.removeIf(p -> p.getTicket().equals(ticketBorrar));
                actualizarTabla();
            }
        });

        add(panelRegistro, BorderLayout.NORTH);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar
        for (Paciente p : pacientes) {
            if (!p.isAtendido()) {
                modeloTabla.addRow(new Object[]{p.getTicket(), p.getId(), p.getHoraFormateada()});
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppClinica().setVisible(true));
    }
}