package view;

import controller.CorreoController;
import exception.CorreoException;
import model.Correo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CorreoAppFrame extends JFrame {
    private final CorreoController controller;
    private final JTextField destinatarioField;
    private final JTextField asuntoField;
    private final JTextArea contenidoArea;
    private final DefaultTableModel tableModel;
    private final JTable correoTable;
    private String correoSeleccionadoId;

    public CorreoAppFrame(CorreoController controller) {
        this.controller = controller;
        this.destinatarioField = new JTextField(30);
        this.asuntoField = new JTextField(30);
        this.contenidoArea = new JTextArea(6, 30);
        this.tableModel = new DefaultTableModel(new String[]{"ID", "Destinatario", "Asunto", "Contenido", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.correoTable = new JTable(tableModel);
        initComponents();
    }

    private void initComponents() {
        setTitle("SmartCampus Notify - Emulación de Correos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(820, 520));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainPanel.add(buildFormPanel(), BorderLayout.NORTH);
        mainPanel.add(buildTablePanel(), BorderLayout.CENTER);
        mainPanel.add(buildButtonPanel(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        cargarCorreos();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Crear / Actualizar correo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Destinatario:"), gbc);
        gbc.gridx = 1;
        panel.add(destinatarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Asunto:"), gbc);
        gbc.gridx = 1;
        panel.add(asuntoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Contenido:"), gbc);
        gbc.gridx = 1;
        JScrollPane contenidoScroll = new JScrollPane(contenidoArea);
        panel.add(contenidoScroll, gbc);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        correoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        correoTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                llenarFormularioDesdeTabla();
            }
        });
        JScrollPane scrollPane = new JScrollPane(correoTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Correos almacenados"));
        return scrollPane;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton enviarButton = new JButton("Enviar");
        enviarButton.addActionListener(this::onEnviar);
        panel.add(enviarButton);

        JButton actualizarButton = new JButton("Actualizar");
        actualizarButton.addActionListener(this::onActualizar);
        panel.add(actualizarButton);

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(this::onEliminar);
        panel.add(eliminarButton);

        JButton verButton = new JButton("Ver correos");
        verButton.addActionListener(e -> cargarCorreos());
        panel.add(verButton);

        JButton limpiarButton = new JButton("Limpiar");
        limpiarButton.addActionListener(e -> limpiarFormulario());
        panel.add(limpiarButton);

        return panel;
    }

    private void onEnviar(ActionEvent event) {
        try {
            String mensaje = controller.enviarCorreo(
                destinatarioField.getText(),
                asuntoField.getText(),
                contenidoArea.getText()
            );
            JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarCorreos();
            limpiarFormulario();
        } catch (CorreoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onActualizar(ActionEvent event) {
        if (correoSeleccionadoId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un correo para actualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String mensaje = controller.actualizarCorreo(
                correoSeleccionadoId,
                destinatarioField.getText(),
                asuntoField.getText(),
                contenidoArea.getText()
            );
            JOptionPane.showMessageDialog(this, mensaje, "Actualizado", JOptionPane.INFORMATION_MESSAGE);
            cargarCorreos();
            limpiarFormulario();
        } catch (CorreoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEliminar(ActionEvent event) {
        if (correoSeleccionadoId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un correo para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el correo seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            controller.eliminarCorreo(correoSeleccionadoId);
            JOptionPane.showMessageDialog(this, "Correo eliminado correctamente.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
            cargarCorreos();
            limpiarFormulario();
        } catch (CorreoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCorreos() {
        tableModel.setRowCount(0);
        List<Correo> correos = controller.obtenerCorreos();
        for (Correo correo : correos) {
            tableModel.addRow(new Object[]{
                correo.getId(),
                correo.getDestinatario(),
                correo.getAsunto(),
                correo.getContenido(),
                correo.getFecha()
            });
        }
    }

    private void llenarFormularioDesdeTabla() {
        int fila = correoTable.getSelectedRow();
        if (fila < 0) {
            return;
        }
        correoSeleccionadoId = (String) tableModel.getValueAt(fila, 0);
        destinatarioField.setText((String) tableModel.getValueAt(fila, 1));
        asuntoField.setText((String) tableModel.getValueAt(fila, 2));
        contenidoArea.setText((String) tableModel.getValueAt(fila, 3));
    }

    private void limpiarFormulario() {
        correoSeleccionadoId = null;
        destinatarioField.setText("");
        asuntoField.setText("");
        contenidoArea.setText("");
        correoTable.clearSelection();
    }
}
