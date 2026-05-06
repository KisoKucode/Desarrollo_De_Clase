package controller;

import exception.CorreoException;
import model.Tarea;
import service.ITareaService;

import java.time.LocalDate;
import java.util.List;

public class TareaController {
    private final ITareaService tareaService;

    public TareaController(ITareaService tareaService) {
        this.tareaService = tareaService;
    }

    public String crearTarea(String titulo, String descripcion, String fechaLimite, String materia) {
        LocalDate fecha = LocalDate.parse(fechaLimite);
        return tareaService.crearTarea(titulo, descripcion, fecha, materia);
    }

    public List<Tarea> obtenerTareas() {
        return tareaService.listarTareas();
    }

    public List<Tarea> obtenerTareasProximas() {
        return tareaService.listarTareasProximas();
    }

    public List<Tarea> obtenerTareasVencidas() {
        return tareaService.listarTareasVencidas();
    }

    public String actualizarTarea(String id, String titulo, String descripcion, String fechaLimite, String materia, boolean completada) {
        Tarea tarea = new Tarea(id, titulo, descripcion, fechaLimite, materia, completada);
        return tareaService.actualizarTarea(tarea);
    }

    public void eliminarTarea(String id) {
        tareaService.eliminarTarea(id);
    }

    public String marcarCompletada(String id) {
        return tareaService.marcarCompletada(id);
    }
}
