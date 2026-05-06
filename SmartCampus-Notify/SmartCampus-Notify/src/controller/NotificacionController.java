package controller;

import model.Notificacion;
import service.INotificacionService;

import java.util.List;

public class NotificacionController {
    private final INotificacionService notificacionService;

    public NotificacionController(INotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    public void generarNotificaciones() {
        notificacionService.generarNotificacionesAcademicas();
    }

    public List<Notificacion> obtenerNotificaciones() {
        return notificacionService.obtenerNotificaciones();
    }

    public List<Notificacion> obtenerNoLeidas() {
        return notificacionService.obtenerNotificacionesNoLeidas();
    }

    public void marcarComoLeida(String notificacionId) {
        notificacionService.marcarComoLeida(notificacionId);
    }

    public int contarNoLeidas() {
        return notificacionService.obtenerNotificacionesNoLeidas().size();
    }
}
