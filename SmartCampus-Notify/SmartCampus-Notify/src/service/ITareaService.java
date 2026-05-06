package service;

import exception.CorreoException;
import model.Tarea;

import java.time.LocalDate;
import java.util.List;

public interface ITareaService {
    String crearTarea(String titulo, String descripcion, LocalDate fechaLimite, String materia) throws CorreoException;
    List<Tarea> listarTareas();
    List<Tarea> listarTareasProximas();
    List<Tarea> listarTareasVencidas();
    String actualizarTarea(Tarea tarea) throws CorreoException;
    void eliminarTarea(String tareaId) throws CorreoException;
    String marcarCompletada(String tareaId) throws CorreoException;
}
