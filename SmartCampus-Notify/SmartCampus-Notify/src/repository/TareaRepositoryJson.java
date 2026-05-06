package repository;

import exception.CorreoException;
import model.Tarea;
import util.JsonUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TareaRepositoryJson implements ITareaRepository {
    private static final Path STORAGE_PATH = Paths.get("tareas.json");

    @Override
    public List<Tarea> obtenerTodas() {
        try {
            if (Files.notExists(STORAGE_PATH)) {
                return new ArrayList<>();
            }
            String json = Files.readString(STORAGE_PATH);
            return JsonUtil.parseTareaArray(json);
        } catch (IOException ex) {
            throw new CorreoException("No se pudo leer el archivo de tareas", ex);
        } catch (Exception ex) {
            System.err.println("Advertencia: Error al parsear tareas.json, iniciando con lista vacía");
            return new ArrayList<>();
        }
    }

    @Override
    public Tarea guardar(Tarea tarea) {
        tarea.asignarIdSiFalta();
        List<Tarea> tareas = obtenerTodas();
        tareas.add(tarea);
        escribirArchivo(tareas);
        return tarea;
    }

    @Override
    public void actualizar(Tarea tarea) {
        List<Tarea> tareas = obtenerTodas();
        boolean encontrado = false;
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId().equals(tarea.getId())) {
                tareas.set(i, tarea);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new CorreoException("No se encontró la tarea para actualizar");
        }
        escribirArchivo(tareas);
    }

    @Override
    public void eliminar(String id) {
        List<Tarea> tareas = obtenerTodas();
        boolean eliminado = tareas.removeIf(tarea -> tarea.getId().equals(id));
        if (!eliminado) {
            throw new CorreoException("No se encontró la tarea para eliminar");
        }
        escribirArchivo(tareas);
    }

    @Override
    public Tarea buscarPorId(String id) {
        return obtenerTodas().stream()
            .filter(tarea -> tarea.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Tarea> obtenerProximas() {
        return obtenerTodas().stream()
            .filter(Tarea::esProxima)
            .filter(tarea -> !tarea.isCompletada())
            .collect(Collectors.toList());
    }

    @Override
    public List<Tarea> obtenerVencidas() {
        return obtenerTodas().stream()
            .filter(Tarea::estaVencida)
            .filter(tarea -> !tarea.isCompletada())
            .collect(Collectors.toList());
    }

    private void escribirArchivo(List<Tarea> tareas) {
        try {
            String json = JsonUtil.tareaArrayToJson(tareas);
            Files.writeString(STORAGE_PATH, json);
        } catch (IOException ex) {
            throw new CorreoException("No se pudo guardar el archivo de tareas", ex);
        }
    }
}
