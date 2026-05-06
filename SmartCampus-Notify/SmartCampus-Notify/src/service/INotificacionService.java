package service;

import model.Correo;
import model.Notificacion;
import model.Notificacion.TipoNotificacion;
import model.Tarea;

import java.util.ArrayList;
import java.util.List;

public interface INotificacionService {
    void generarNotificacionesAcademicas();
    List<Notificacion> obtenerNotificaciones();
    List<Notificacion> obtenerNotificacionesNoLeidas();
    void marcarComoLeida(String notificacionId);
    void limpiarNotificacionesAnteriores();
}
