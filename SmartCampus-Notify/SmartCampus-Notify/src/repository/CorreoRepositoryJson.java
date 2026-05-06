package repository;

import exception.CorreoException;
import model.Correo;
import util.JsonUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CorreoRepositoryJson implements ICorreoRepository {
    private static final Path STORAGE_PATH = Paths.get("correos.json");

    @Override
    public List<Correo> obtenerTodos() {
        try {
            if (Files.notExists(STORAGE_PATH)) {
                return new ArrayList<>();
            }
            String json = Files.readString(STORAGE_PATH);
            return JsonUtil.parseCorreoArray(json);
        } catch (IOException ex) {
            throw new CorreoException("No se pudo leer el archivo de correos", ex);
        } catch (Exception ex) {
            System.err.println("Advertencia: Error al parsear correos.json, iniciando con lista vacía");
            return new ArrayList<>();
        }
    }

    @Override
    public Correo guardar(Correo correo) {
        correo.asignarIdSiFalta();
        List<Correo> correos = obtenerTodos();
        correos.add(correo);
        escribirArchivo(correos);
        return correo;
    }

    @Override
    public void actualizar(Correo correo) {
        List<Correo> correos = obtenerTodos();
        boolean encontrado = false;
        for (int i = 0; i < correos.size(); i++) {
            if (correos.get(i).getId().equals(correo.getId())) {
                correos.set(i, correo);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new CorreoException("No se encontró el correo para actualizar");
        }
        escribirArchivo(correos);
    }

    @Override
    public void eliminar(String id) {
        List<Correo> correos = obtenerTodos();
        boolean eliminado = correos.removeIf(correo -> correo.getId().equals(id));
        if (!eliminado) {
            throw new CorreoException("No se encontró el correo para eliminar");
        }
        escribirArchivo(correos);
    }

    @Override
    public Correo buscarPorId(String id) {
        return obtenerTodos().stream()
            .filter(correo -> correo.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean existePorDestinatarioYAsunto(String destinatario, String asunto, String idExcluido) {
        return obtenerTodos().stream()
            .anyMatch(correo -> correo.getDestinatario().equalsIgnoreCase(destinatario)
                && correo.getAsunto().equalsIgnoreCase(asunto)
                && (idExcluido == null || !correo.getId().equals(idExcluido)));
    }

    private void escribirArchivo(List<Correo> correos) {
        try {
            String json = JsonUtil.correoArrayToJson(correos);
            Files.writeString(STORAGE_PATH, json);
        } catch (IOException ex) {
            throw new CorreoException("No se pudo guardar el archivo de correos", ex);
        }
    }
}
