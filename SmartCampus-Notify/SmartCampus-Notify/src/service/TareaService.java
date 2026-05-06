package service;

import exception.CorreoException;
import model.Tarea;
import repository.ITareaRepository;

import java.time.LocalDate;
import java.util.List;

public class TareaService implements ITareaService {
    private final ITareaRepository repository;

    public TareaService(ITareaRepository repository) {
        this.repository = repository;
    }

    @Override
    public String crearTarea(String titulo, String descripcion, LocalDate fechaLimite, String materia) {
        validarTarea(titulo, descripcion, fechaLimite, materia);
        Tarea tarea = new Tarea(titulo, descripcion, fechaLimite, materia);
        repository.guardar(tarea);
        return String.format("Tarea '%s' creada correctamente", titulo);
    }

    @Override
    public List<Tarea> listarTareas() {
        return repository.obtenerTodas();
    }

    @Override
    public List<Tarea> listarTareasProximas() {
        return repository.obtenerProximas();
    }

    @Override
    public List<Tarea> listarTareasVencidas() {
        return repository.obtenerVencidas();
    }

    @Override
    public String actualizarTarea(Tarea tarea) {
        validarTarea(tarea.getTitulo(), tarea.getDescripcion(), tarea.getFechaLimite(), tarea.getMaterial());
        if (repository.buscarPorId(tarea.getId()) == null) {
            throw new CorreoException("La tarea no existe.");
        }
        repository.actualizar(tarea);
        return "Tarea actualizada correctamente";
    }

    @Override
    public void eliminarTarea(String tareaId) {
        if (tareaId == null || tareaId.isBlank()) {
            throw new CorreoException("ID de tarea inválido.");
        }
        repository.eliminar(tareaId);
    }

    @Override
    public String marcarCompletada(String tareaId) {
        if (tareaId == null || tareaId.isBlank()) {
            throw new CorreoException("ID de tarea inválido.");
        }
        Tarea tarea = repository.buscarPorId(tareaId);
        if (tarea == null) {
            throw new CorreoException("La tarea no existe.");
        }
        tarea.setCompletada(!tarea.isCompletada());
        repository.actualizar(tarea);
        return String.format("Tarea marcada como %s", tarea.isCompletada() ? "completada" : "pendiente");
    }

    private void validarTarea(String titulo, String descripcion, LocalDate fechaLimite, String materia) {
        if (titulo == null || titulo.isBlank()) {
            throw new CorreoException("El título es obligatorio.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new CorreoException("La descripción es obligatoria.");
        }
        if (fechaLimite == null) {
            throw new CorreoException("La fecha límite es obligatoria.");
        }
        if (materia == null || materia.isBlank()) {
            throw new CorreoException("La materia es obligatoria.");
        }
    }
}
