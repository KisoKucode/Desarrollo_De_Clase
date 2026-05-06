package repository;

import model.Tarea;

import java.util.List;

public interface ITareaRepository {
    List<Tarea> obtenerTodas();
    Tarea guardar(Tarea tarea);
    void actualizar(Tarea tarea);
    void eliminar(String id);
    Tarea buscarPorId(String id);
    List<Tarea> obtenerProximas();
    List<Tarea> obtenerVencidas();
}
