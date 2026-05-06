package service;

import exception.CorreoException;
import model.Correo;
import repository.ICorreoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class CorreoService implements ICorreoService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final DateTimeFormatter FECHA_FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ICorreoRepository repository;

    public CorreoService(ICorreoRepository repository) {
        this.repository = repository;
    }

    @Override
    public String enviarCorreo(Correo correo) {
        validarCorreo(correo, false);
        if (repository.existePorDestinatarioYAsunto(correo.getDestinatario(), correo.getAsunto(), null)) {
            throw new CorreoException("Ya existe un correo con el mismo destinatario y asunto.");
        }
        correo.asignarIdSiFalta();
        correo.setFecha(LocalDateTime.now().format(FECHA_FORMATO));
        repository.guardar(correo);
        return String.format("Simulación: correo enviado a %s el %s", correo.getDestinatario(), correo.getFecha());
    }

    @Override
    public List<Correo> listarCorreos() {
        return repository.obtenerTodos();
    }

    @Override
    public String actualizarCorreo(Correo correo) {
        validarCorreo(correo, true);
        if (repository.buscarPorId(correo.getId()) == null) {
            throw new CorreoException("No se encontró el correo para actualizar.");
        }
        if (repository.existePorDestinatarioYAsunto(correo.getDestinatario(), correo.getAsunto(), correo.getId())) {
            throw new CorreoException("Ya existe otro correo con el mismo destinatario y asunto.");
        }
        correo.setFecha(LocalDateTime.now().format(FECHA_FORMATO));
        repository.actualizar(correo);
        return String.format("Correo actualizado correctamente. Fecha de modificación: %s", correo.getFecha());
    }

    @Override
    public void eliminarCorreo(String correoId) {
        if (correoId == null || correoId.isBlank()) {
            throw new CorreoException("Seleccione un correo válido para eliminar.");
        }
        repository.eliminar(correoId);
    }

    @Override
    public Correo buscarCorreo(String correoId) {
        if (correoId == null || correoId.isBlank()) {
            throw new CorreoException("ID de correo inválido.");
        }
        Correo correo = repository.buscarPorId(correoId);
        if (correo == null) {
            throw new CorreoException("No se encontró el correo solicitado.");
        }
        return correo;
    }

    private void validarCorreo(Correo correo, boolean esActualizacion) {
        if (correo == null) {
            throw new CorreoException("El correo no puede estar vacío.");
        }
        if (correo.getDestinatario() == null || correo.getDestinatario().isBlank()) {
            throw new CorreoException("El destinatario es obligatorio.");
        }
        if (!EMAIL_PATTERN.matcher(correo.getDestinatario().trim()).matches()) {
            throw new CorreoException("El destinatario debe ser un correo válido.");
        }
        if (correo.getAsunto() == null || correo.getAsunto().isBlank()) {
            throw new CorreoException("El asunto es obligatorio.");
        }
        if (correo.getAsunto().trim().length() < 5) {
            throw new CorreoException("El asunto debe tener al menos 5 caracteres.");
        }
        if (correo.getContenido() == null || correo.getContenido().isBlank()) {
            throw new CorreoException("El contenido es obligatorio.");
        }
        if (esActualizacion && (correo.getId() == null || correo.getId().isBlank())) {
            throw new CorreoException("El correo debe tener un ID válido para actualizar.");
        }
    }
}
