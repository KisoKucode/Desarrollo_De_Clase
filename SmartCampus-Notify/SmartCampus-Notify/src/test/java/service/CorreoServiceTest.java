package service;

import exception.CorreoException;
import model.Correo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ICorreoRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CorreoServiceTest {
    private CorreoService servicio;

    @BeforeEach
    void setUp() {
        servicio = new CorreoService(new InMemoryCorreoRepository());
    }

    @Test
    void enviarCorreoValidoSimulaYPersiste() {
        String mensaje = servicio.enviarCorreo(new Correo("estudiante@universidad.edu", "Prueba asunto", "Contenido de la prueba."));
        assertTrue(mensaje.contains("Simulación"));
        assertFalse(servicio.listarCorreos().isEmpty());
    }

    @Test
    void noPermiteDestinatarioInvalido() {
        Correo correo = new Correo("destinatario-no-valido", "Asunto válido", "Contenido.");
        CorreoException error = assertThrows(CorreoException.class, () -> servicio.enviarCorreo(correo));
        assertEquals("El destinatario debe ser un correo válido.", error.getMessage());
    }

    @Test
    void noPermiteAsuntoCorto() {
        Correo correo = new Correo("estudiante@universidad.edu", "Hola", "Contenido válido.");
        CorreoException error = assertThrows(CorreoException.class, () -> servicio.enviarCorreo(correo));
        assertEquals("El asunto debe tener al menos 5 caracteres.", error.getMessage());
    }

    @Test
    void noPermiteCorreosDuplicados() {
        Correo primerCorreo = new Correo("estudiante@universidad.edu", "Asunto duplicado", "Contenido inicial.");
        servicio.enviarCorreo(primerCorreo);
        Correo segundoCorreo = new Correo("estudiante@universidad.edu", "Asunto duplicado", "Otro contenido.");
        CorreoException error = assertThrows(CorreoException.class, () -> servicio.enviarCorreo(segundoCorreo));
        assertEquals("Ya existe un correo con el mismo destinatario y asunto.", error.getMessage());
    }

    private static class InMemoryCorreoRepository implements ICorreoRepository {
        private final List<Correo> almacen = new ArrayList<>();

        @Override
        public List<Correo> obtenerTodos() {
            return new ArrayList<>(almacen);
        }

        @Override
        public Correo guardar(Correo correo) {
            almacen.add(correo);
            return correo;
        }

        @Override
        public void actualizar(Correo correo) {
            for (int i = 0; i < almacen.size(); i++) {
                if (almacen.get(i).getId().equals(correo.getId())) {
                    almacen.set(i, correo);
                    return;
                }
            }
            throw new IllegalStateException("Correo no encontrado");
        }

        @Override
        public void eliminar(String id) {
            almacen.removeIf(correo -> correo.getId().equals(id));
        }

        @Override
        public Correo buscarPorId(String id) {
            return almacen.stream().filter(correo -> correo.getId().equals(id)).findFirst().orElse(null);
        }

        @Override
        public boolean existePorDestinatarioYAsunto(String destinatario, String asunto, String idExcluido) {
            return almacen.stream().anyMatch(correo -> correo.getDestinatario().equalsIgnoreCase(destinatario)
                && correo.getAsunto().equalsIgnoreCase(asunto)
                && (idExcluido == null || !correo.getId().equals(idExcluido)));
        }
    }
}
