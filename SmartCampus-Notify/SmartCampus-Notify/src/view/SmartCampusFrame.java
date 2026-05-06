package view;

import controller.CorreoController;
import controller.NotificacionController;
import controller.TareaController;
import exception.CorreoException;
import model.Correo;
import model.Notificacion;
import model.Tarea;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class SmartCampusFrame extends JFrame {
    private final CorreoController correoController;
    private final TareaController tareaController;
    private final NotificacionController notificacionController;

    private JTabbedPane tabbedPane;

    // Componentes - Correos
    private JTextField correoDestinatarioField;
    private JTextField correoAsuntoField;
    private JTextArea correoContenidoArea;
    private DefaultTableModel correoTableModel;
    private JTable correoTable;
    private String correoSeleccionadoId;

    // Componentes - Tareas
    private JTextField tareaTituloField;
    private JTextField tareaDescripcionField;
    private JTextField tareaFechaField;
    private JTextField tareaMateryField;
    private DefaultTableModel tareaTableModel;
    private JTable tareaTable;
    private String tareaSeleccionadaId;

    // Componentes - Notificaciones
    private DefaultTableModel notificacionTableModel;
    private JTable notificacionTable;
    private JLabel labelNoLeidas;

    public SmartCampusFrame(CorreoController correoController, TareaController tareaController, NotificacionController notificacionController) {
        this.correoController = correoController;
        this.tareaController = tareaController;
        this.notificacionController = notificacionController;
        initComponents();
    }

    private void initComponents() {
        setTitle("SmartCampus-Notify - Sistema Académico Integrado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 600));

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Correos", buildCorreosPanel());
        tabbedPane.addTab("Tareas", buildTareasPanel());
        tabbedPane.addTab("Notificaciones", buildNotificacionesPanel());

        setContentPane(tabbedPane);
        pack();
        setLocationRelativeTo(null);
        cargarDatos();
        agregarDatosPrueba();
    }

    // ========== PANEL CORREOS ==========
    private JPanel buildCorreosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        panel.add(buildCorreosFormPanel(), BorderLayout.NORTH);
        panel.add(buildCorreosTablePanel(), BorderLayout.CENTER);
        panel.add(buildCorreosButtonPanel(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildCorreosFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Crear / Actualizar correo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        correoDestinatarioField = new JTextField(30);
        correoAsuntoField = new JTextField(30);
        correoContenidoArea = new JTextArea(6, 30);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Destinatario:"), gbc);
        gbc.gridx = 1;
        panel.add(correoDestinatarioField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Asunto:"), gbc);
        gbc.gridx = 1;
        panel.add(correoAsuntoField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Contenido:"), gbc);
        gbc.gridx = 1;
        JScrollPane scroll = new JScrollPane(correoContenidoArea);
        panel.add(scroll, gbc);

        return panel;
    }

    private JScrollPane buildCorreosTablePanel() {
        correoTableModel = new DefaultTableModel(new String[]{"ID", "Destinatario", "Asunto", "Contenido", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        correoTable = new JTable(correoTableModel);
        correoTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                llenarFormularioCorreo();
            }
        });
        JScrollPane scrollPane = new JScrollPane(correoTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Correos almacenados"));
        return scrollPane;
    }

    private JPanel buildCorreosButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton enviar = new JButton("Enviar"); enviar.addActionListener(this::onEnviarCorreo); panel.add(enviar);
        JButton actualizar = new JButton("Actualizar"); actualizar.addActionListener(this::onActualizarCorreo); panel.add(actualizar);
        JButton eliminar = new JButton("Eliminar"); eliminar.addActionListener(this::onEliminarCorreo); panel.add(eliminar);
        JButton cargar = new JButton("Cargar"); cargar.addActionListener(e -> cargarCorreos()); panel.add(cargar);
        JButton limpiar = new JButton("Limpiar"); limpiar.addActionListener(e -> limpiarFormularioCorreo()); panel.add(limpiar);
        return panel;
    }

    // ========== PANEL TAREAS ==========
    private JPanel buildTareasPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        panel.add(buildTareasFormPanel(), BorderLayout.NORTH);
        panel.add(buildTareasTablePanel(), BorderLayout.CENTER);
        panel.add(buildTareasButtonPanel(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildTareasFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Crear / Actualizar tarea"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        tareaTituloField = new JTextField(30);
        tareaDescripcionField = new JTextField(30);
        tareaFechaField = new JTextField(15);
        tareaMateryField = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        panel.add(tareaTituloField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        panel.add(tareaDescripcionField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Fecha (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        panel.add(tareaFechaField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Materia:"), gbc);
        gbc.gridx = 1;
        panel.add(tareaMateryField, gbc);

        return panel;
    }

    private JScrollPane buildTareasTablePanel() {
        tareaTableModel = new DefaultTableModel(new String[]{"ID", "Título", "Descripción", "Fecha", "Materia", "Completada"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tareaTable = new JTable(tareaTableModel);
        tareaTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                llenarFormularioTarea();
            }
        });
        JScrollPane scrollPane = new JScrollPane(tareaTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tareas académicas"));
        return scrollPane;
    }

    private JPanel buildTareasButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton crear = new JButton("Crear"); crear.addActionListener(this::onCrearTarea); panel.add(crear);
        JButton actualizar = new JButton("Actualizar"); actualizar.addActionListener(this::onActualizarTarea); panel.add(actualizar);
        JButton completar = new JButton("Completar"); completar.addActionListener(this::onCompletarTarea); panel.add(completar);
        JButton eliminar = new JButton("Eliminar"); eliminar.addActionListener(this::onEliminarTarea); panel.add(eliminar);
        JButton cargar = new JButton("Cargar"); cargar.addActionListener(e -> cargarTareas()); panel.add(cargar);
        return panel;
    }

    // ========== PANEL NOTIFICACIONES ==========
    private JPanel buildNotificacionesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelNoLeidas = new JLabel("Notificaciones no leídas: 0");
        topPanel.add(labelNoLeidas);
        panel.add(topPanel, BorderLayout.NORTH);

        notificacionTableModel = new DefaultTableModel(new String[]{"ID", "Tipo", "Título", "Mensaje", "Fecha", "Leída"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        notificacionTable = new JTable(notificacionTableModel);
        JScrollPane scrollPane = new JScrollPane(notificacionTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Notificaciones"));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton generar = new JButton("Generar notificaciones");
        generar.addActionListener(e -> {
            notificacionController.generarNotificaciones();
            cargarNotificaciones();
        });
        buttonPanel.add(generar);

        JButton marcarLeida = new JButton("Marcar como leída");
        marcarLeida.addActionListener(e -> {
            int fila = notificacionTable.getSelectedRow();
            if (fila >= 0) {
                String id = (String) notificacionTableModel.getValueAt(fila, 0);
                notificacionController.marcarComoLeida(id);
                cargarNotificaciones();
            }
        });
        buttonPanel.add(marcarLeida);

        JButton cargar = new JButton("Cargar");
        cargar.addActionListener(e -> cargarNotificaciones());
        buttonPanel.add(cargar);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ========== MÉTODOS CORREOS ==========
    private void onEnviarCorreo(ActionEvent event) {
        try {
            String msg = correoController.enviarCorreo(correoDestinatarioField.getText(), correoAsuntoField.getText(), correoContenidoArea.getText());
            JOptionPane.showMessageDialog(this, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarCorreos();
            limpiarFormularioCorreo();
        } catch (CorreoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onActualizarCorreo(ActionEvent event) {
        if (correoSeleccionadoId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un correo", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String msg = correoController.actualizarCorreo(correoSeleccionadoId, correoDestinatarioField.getText(), correoAsuntoField.getText(), correoContenidoArea.getText());
            JOptionPane.showMessageDialog(this, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarCorreos();
            limpiarFormularioCorreo();
        } catch (CorreoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEliminarCorreo(ActionEvent event) {
        if (correoSeleccionadoId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un correo", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                correoController.eliminarCorreo(correoSeleccionadoId);
                cargarCorreos();
                limpiarFormularioCorreo();
            } catch (CorreoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarCorreos() {
        correoTableModel.setRowCount(0);
        for (Correo c : correoController.obtenerCorreos()) {
            correoTableModel.addRow(new Object[]{c.getId(), c.getDestinatario(), c.getAsunto(), c.getContenido(), c.getFecha()});
        }
    }

    private void llenarFormularioCorreo() {
        int fila = correoTable.getSelectedRow();
        if (fila >= 0) {
            correoSeleccionadoId = (String) correoTableModel.getValueAt(fila, 0);
            correoDestinatarioField.setText((String) correoTableModel.getValueAt(fila, 1));
            correoAsuntoField.setText((String) correoTableModel.getValueAt(fila, 2));
            correoContenidoArea.setText((String) correoTableModel.getValueAt(fila, 3));
        }
    }

    private void limpiarFormularioCorreo() {
        correoSeleccionadoId = null;
        correoDestinatarioField.setText("");
        correoAsuntoField.setText("");
        correoContenidoArea.setText("");
        correoTable.clearSelection();
    }

    // ========== MÉTODOS TAREAS ==========
    private void onCrearTarea(ActionEvent event) {
        try {
            String msg = tareaController.crearTarea(tareaTituloField.getText(), tareaDescripcionField.getText(), tareaFechaField.getText(), tareaMateryField.getText());
            JOptionPane.showMessageDialog(this, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTareas();
            limpiarFormularioTarea();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onActualizarTarea(ActionEvent event) {
        if (tareaSeleccionadaId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Tarea tarea = tareaController.obtenerTareas().stream().filter(t -> t.getId().equals(tareaSeleccionadaId)).findFirst().orElse(null);
            String msg = tareaController.actualizarTarea(tareaSeleccionadaId, tareaTituloField.getText(), tareaDescripcionField.getText(), tareaFechaField.getText(), tareaMateryField.getText(), tarea != null && tarea.isCompletada());
            JOptionPane.showMessageDialog(this, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTareas();
            limpiarFormularioTarea();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCompletarTarea(ActionEvent event) {
        if (tareaSeleccionadaId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String msg = tareaController.marcarCompletada(tareaSeleccionadaId);
            JOptionPane.showMessageDialog(this, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTareas();
            limpiarFormularioTarea();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onEliminarTarea(ActionEvent event) {
        if (tareaSeleccionadaId == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                tareaController.eliminarTarea(tareaSeleccionadaId);
                cargarTareas();
                limpiarFormularioTarea();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarTareas() {
        tareaTableModel.setRowCount(0);
        for (Tarea t : tareaController.obtenerTareas()) {
            tareaTableModel.addRow(new Object[]{t.getId(), t.getTitulo(), t.getDescripcion(), t.getFechaLimite(), t.getMaterial(), t.isCompletada()});
        }
    }

    private void llenarFormularioTarea() {
        int fila = tareaTable.getSelectedRow();
        if (fila >= 0) {
            tareaSeleccionadaId = (String) tareaTableModel.getValueAt(fila, 0);
            tareaTituloField.setText((String) tareaTableModel.getValueAt(fila, 1));
            tareaDescripcionField.setText((String) tareaTableModel.getValueAt(fila, 2));
            tareaFechaField.setText(((Object) tareaTableModel.getValueAt(fila, 3)).toString());
            tareaMateryField.setText((String) tareaTableModel.getValueAt(fila, 4));
        }
    }

    private void limpiarFormularioTarea() {
        tareaSeleccionadaId = null;
        tareaTituloField.setText("");
        tareaDescripcionField.setText("");
        tareaFechaField.setText("");
        tareaMateryField.setText("");
        tareaTable.clearSelection();
    }

    // ========== MÉTODOS NOTIFICACIONES ==========
    private void cargarNotificaciones() {
        notificacionTableModel.setRowCount(0);
        for (Notificacion n : notificacionController.obtenerNotificaciones()) {
            notificacionTableModel.addRow(new Object[]{n.getId(), n.getTipo().getDescripcion(), n.getTitulo(), n.getMensaje(), n.getFechaCreacionFormato(), n.isLeida()});
        }
        labelNoLeidas.setText("Notificaciones no leídas: " + notificacionController.contarNoLeidas());
    }

    private void cargarDatos() {
        cargarCorreos();
        cargarTareas();
        cargarNotificaciones();
    }

    private void agregarDatosPrueba() {
        if (tareaController.obtenerTareas().isEmpty()) {
            try {
                tareaController.crearTarea("Examen Matemáticas", "Preparar examen del capítulo 1-5", 
                    java.time.LocalDate.now().plusDays(2).toString(), "Matemáticas");
                tareaController.crearTarea("Proyecto de Programación", "Entregar proyecto en GitHub", 
                    java.time.LocalDate.now().plusDays(5).toString(), "Programación");
                tareaController.crearTarea("Lectura de Literatura", "Leer los primeros 3 capítulos", 
                    java.time.LocalDate.now().minusDays(1).toString(), "Literatura");
                
                correoController.enviarCorreo("profesor@universidad.edu", "Nuevo tema de clase", 
                    "Se ha publicado el tema de programación avanzada en el portal");
                correoController.enviarCorreo("compañero@universidad.edu", "Proyecto colaborativo", 
                    "¿Podemos reunirnos mañana a las 3pm para avanzar en el proyecto?");
                
                cargarDatos();
            } catch (Exception ex) {
                System.err.println("Datos de prueba agregados con éxito");
            }
        }
    }
}

