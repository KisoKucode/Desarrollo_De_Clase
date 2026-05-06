package service;

import model.Notificacion;
import model.Notificacion.TipoNotificacion;
import model.Tarea;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotificacionService implements INotificacionService {
    private final ITareaService tareaService;
    private final List<Notificacion> notificaciones = new ArrayList<>();

    public NotificacionService(ITareaService tareaService) {
        this.tareaService = tareaService;
    }

    @Override
    public void generarNotificacionesAcademicas() {
        limpiarNotificacionesAnteriores();

        List<Tarea> proximas = tareaService.listarTareasProximas();
        for (Tarea tarea : proximas) {
            String titulo = String.format("Tarea próxima: %s", tarea.getTitulo());
            String mensaje = String.format("La tarea '%s' vence en %d día(s)", tarea.getTitulo(), tarea.diasRestantes());
            Notificacion notif = new Notificacion(titulo, mensaje, TipoNotificacion.TAREA_PROXIMA, tarea.getId());
            notificaciones.add(notif);
        }

        List<Tarea> vencidas = tareaService.listarTareasVencidas();
        for (Tarea tarea : vencidas) {
            String titulo = String.format("Tarea VENCIDA: %s", tarea.getTitulo());
            String mensaje = String.format("La tarea '%s' está vencida", tarea.getTitulo());
            Notificacion notif = new Notificacion(titulo, mensaje, TipoNotificacion.TAREA_VENCIDA, tarea.getId());
            notificaciones.add(notif);
        }
    }

    @Override
    public List<Notificacion> obtenerNotificaciones() {
        return new ArrayList<>(notificaciones);
    }

    @Override
    public List<Notificacion> obtenerNotificacionesNoLeidas() {
        return notificaciones.stream()
            .filter(notif -> !notif.isLeida())
            .collect(Collectors.toList());
    }

    @Override
    public void marcarComoLeida(String notificacionId) {
        notificaciones.stream()
            .filter(notif -> notif.getId().equals(notificacionId))
            .findFirst()
            .ifPresent(notif -> notif.setLeida(true));
    }

    @Override
    public void limpiarNotificacionesAnteriores() {
        LocalDateTime hace24Horas = LocalDateTime.now().minus(24, ChronoUnit.HOURS);
        notificaciones.removeIf(notif -> notif.getFechaCreacion().isBefore(hace24Horas));
    }
}
