package gui;

import enums.TipoPaciente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelos.Paciente;
import servicios.ITurnoService;
import servicios.TurnoServiceImpl;

public class AppClinicaGUI extends JFrame {
    private ITurnoService service;
    private DefaultTableModel tablaModel;
    private JTable tabla;
    private JTextField idField;
    private JComboBox<TipoPaciente> tipoCombo;
    private JTextField ticketField;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

    public AppClinicaGUI() {
        super("Clínica - Turnos");
        this.service = new TurnoServiceImpl();

        this.tablaModel = new DefaultTableModel(new String[]{"Ticket", "ID", "Tipo", "Hora", "Atendido"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tabla = new JTable(tablaModel);
        JScrollPane scroll = new JScrollPane(tabla);

        idField = new JTextField(10);
        tipoCombo = new JComboBox<>(TipoPaciente.values());
        ticketField = new JTextField(10);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener((ActionEvent e) -> {
            String id = idField.getText().trim();
            TipoPaciente tipo = (TipoPaciente) tipoCombo.getSelectedItem();
            if (!id.isEmpty() && tipo != null) {
                service.registrarTurno(id, tipo);
                renderizarTabla();
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            String ticket = ticketField.getText().trim();
            if (!ticket.isEmpty()) {
                service.cancelarTurno(ticket);
                renderizarTabla();
            }
        });

        JButton btnAtender = new JButton("Atender");
        btnAtender.addActionListener(e -> {
            if (service instanceof TurnoServiceImpl) {
                ((TurnoServiceImpl) service).atenderPaciente();
                renderizarTabla();
            }
        });

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> renderizarTabla());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(new JLabel("ID:"));
        controls.add(idField);
        controls.add(new JLabel("Tipo:"));
        controls.add(tipoCombo);
        controls.add(btnRegistrar);
        controls.add(new JLabel("Ticket:"));
        controls.add(ticketField);
        controls.add(btnCancelar);
        controls.add(btnAtender);
        controls.add(btnRefrescar);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(controls, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
    }

    public void renderizarTabla() {
        // limpiar modelo
        int filas = tablaModel.getRowCount();
        for (int i = filas - 1; i >= 0; i--) {
            tablaModel.removeRow(i);
        }

        List<Paciente> siguientes = service.obtenerSiguientes();
        for (Paciente p : siguientes) {
            tablaModel.addRow(new Object[]{
                    p.getTicket(),
                    p.getId(),
                    p.getTipo().name(),
                    p.getHoraSolicitud().format(fmt),
                    p.isAtendido()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppClinicaGUI app = new AppClinicaGUI();
            app.setVisible(true);

            // datos de prueba
            app.service.registrarTurno("ID001", TipoPaciente.ADULTO_MAYOR);
            app.service.registrarTurno("ID002", TipoPaciente.REGULAR);
            app.renderizarTabla();
        });
    }
}
