package service;

import exception.CorreoException;
import model.Correo;

import java.util.List;

public interface ICorreoService {
    String enviarCorreo(Correo correo) throws CorreoException;
    List<Correo> listarCorreos();
    String actualizarCorreo(Correo correo) throws CorreoException;
    void eliminarCorreo(String correoId) throws CorreoException;
    Correo buscarCorreo(String correoId) throws CorreoException;
}
