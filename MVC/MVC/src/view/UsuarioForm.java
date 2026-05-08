package view;
import javax.swing.*;

import model.ProgramaAcademico;

import java.awt.*;

public class UsuarioForm extends JFrame {

    private JTextField txtNombre = new JTextField(20);
    private JTextField txtDNI = new JTextField(10);
    private JTextField txtEmail = new JTextField(20);
    private JTextField txtFecha = new JTextField(10);
    private JComboBox<ProgramaAcademico> cbPrograma =
            new JComboBox<>(ProgramaAcademico.values());
    private JButton btnGuardar = new JButton("Registrar Usuario");

    public UsuarioForm() {
        setTitle("Registro Académico");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("DNI:"));
        add(txtDNI);
        add(new JLabel("Email:"));
        add(txtEmail);
        add(new JLabel("Fecha (YYYY-MM-DD):"));
        add(txtFecha);
        add(new JLabel("Programa:"));
        add(cbPrograma);
        add(new JLabel());
        add(btnGuardar);
    }

    // Getters
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getDNI() { return txtDNI.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getFecha() { return txtFecha.getText().trim(); }
    public ProgramaAcademico getPrograma() {
        return (ProgramaAcademico) cbPrograma.getSelectedItem();
    }

    public JButton getBtnGuardar() { return btnGuardar; }
}
