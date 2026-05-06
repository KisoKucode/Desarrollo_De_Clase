package controller;

import exception.CorreoException;
import model.Correo;
import service.ICorreoService;

import java.util.List;

public class CorreoController {
    private final ICorreoService correoService;

    public CorreoController(ICorreoService correoService) {
        this.correoService = correoService;
    }

    public String enviarCorreo(String destinatario, String asunto, String contenido) {
        Correo correo = new Correo(destinatario, asunto, contenido);
        return correoService.enviarCorreo(correo);
    }

    public List<Correo> obtenerCorreos() {
        return correoService.listarCorreos();
    }

    public String actualizarCorreo(String id, String destinatario, String asunto, String contenido) {
        Correo correo = new Correo(id, destinatario, asunto, contenido, null);
        return correoService.actualizarCorreo(correo);
    }

    public void eliminarCorreo(String id) {
        correoService.eliminarCorreo(id);
    }

    public Correo buscarCorreo(String id) {
        return correoService.buscarCorreo(id);
    }
}
