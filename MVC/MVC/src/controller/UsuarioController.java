package controller;
import javax.swing.*;

import model.Usuario;
import model.ValidacionException;
import model.repository.IUsuarioRepository;
import view.UsuarioForm;

import java.time.LocalDate;

public class UsuarioController {

    private final UsuarioForm view;
    private final IUsuarioRepository repository;

    public UsuarioController(UsuarioForm view, IUsuarioRepository repository) {
        this.view = view;
        this.repository = repository;

        this.view.getBtnGuardar().addActionListener(e -> registrar());
    }

    private void registrar() {
        try {
            Usuario usuario = crearUsuario();
            repository.save(usuario);
            mostrar("Usuario guardado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (ValidacionException e) {
            mostrar(e.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            mostrar("Error interno: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Usuario crearUsuario() throws Exception {
        validar();

        return new Usuario(
                view.getNombre(),
                view.getDNI(),
                view.getEmail(),
                LocalDate.parse(view.getFecha()),
                view.getPrograma()
        );
    }

    private void validar() throws ValidacionException {
        if (view.getNombre().isEmpty())
            throw new ValidacionException("El nombre es obligatorio");

        if (!view.getEmail().contains("@"))
            throw new ValidacionException("Email inválido");

        if (view.getDNI().length() < 7)
            throw new ValidacionException("DNI inválido");
    }

    private void mostrar(String msg, String titulo, int tipo) {
        JOptionPane.showMessageDialog(view, msg, titulo, tipo);
    }
}