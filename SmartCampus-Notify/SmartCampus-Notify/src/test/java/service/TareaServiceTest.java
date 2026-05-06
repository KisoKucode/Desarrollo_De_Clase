package service;

import exception.CorreoException;
import model.Tarea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ITareaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TareaServiceTest {
    private TareaService servicio;

    @BeforeEach
    void setUp() {
        servicio = new TareaService(new InMemoryTareaRepository());
    }

    @Test
    void crearTareaValida() {
        String mensaje = servicio.crearTarea("Examen Matemáticas", "Preparar examen parcial", LocalDate.now().plusDays(5), "Matemáticas");
        assertTrue(mensaje.contains("creada correctamente"));
        assertEquals(1, servicio.listarTareas().size());
    }

    @Test
    void noPermiteTituloVacio() {
        CorreoException error = assertThrows(CorreoException.class, () -> 
            servicio.crearTarea("", "Descripción", LocalDate.now().plusDays(1), "Materia")
        );
        assertEquals("El título es obligatorio.", error.getMessage());
    }

    @Test
    void noPermiteDescripcionVacia() {
        CorreoException error = assertThrows(CorreoException.class, () -> 
            servicio.crearTarea("Título", "", LocalDate.now().plusDays(1), "Materia")
        );
        assertEquals("La descripción es obligatoria.", error.getMessage());
    }

    @Test
    void detectaTareasProximas() {
        servicio.crearTarea("Tarea próxima", "Descripción", LocalDate.now().plusDays(2), "Materia");
        servicio.crearTarea("Tarea lejana", "Descripción", LocalDate.now().plusDays(10), "Materia");
        
        List<Tarea> proximas = servicio.listarTareasProximas();
        assertEquals(1, proximas.size());
        assertEquals("Tarea próxima", proximas.get(0).getTitulo());
    }

    @Test
    void detectaTareasVencidas() {
        servicio.crearTarea("Tarea vencida", "Descripción", LocalDate.now().minusDays(5), "Materia");
        servicio.crearTarea("Tarea vigente", "Descripción", LocalDate.now().plusDays(1), "Materia");
        
        List<Tarea> vencidas = servicio.listarTareasVencidas();
        assertEquals(1, vencidas.size());
        assertEquals("Tarea vencida", vencidas.get(0).getTitulo());
    }

    @Test
    void marcarCompletadaCambiaEstado() {
        String msg1 = servicio.crearTarea("Tarea", "Desc", LocalDate.now().plusDays(1), "Materia");
        Tarea tarea = servicio.listarTareas().get(0);
        assertFalse(tarea.isCompletada());
        
        servicio.marcarCompletada(tarea.getId());
        Tarea actualizada = servicio.listarTareas().get(0);
        assertTrue(actualizada.isCompletada());
    }

    private static class InMemoryTareaRepository implements ITareaRepository {
        private final List<Tarea> almacen = new ArrayList<>();

        @Override
        public List<Tarea> obtenerTodas() {
            return new ArrayList<>(almacen);
        }

        @Override
        public Tarea guardar(Tarea tarea) {
            tarea.asignarIdSiFalta();
            almacen.add(tarea);
            return tarea;
        }

        @Override
        public void actualizar(Tarea tarea) {
            for (int i = 0; i < almacen.size(); i++) {
                if (almacen.get(i).getId().equals(tarea.getId())) {
                    almacen.set(i, tarea);
                    return;
                }
            }
        }

        @Override
        public void eliminar(String id) {
            almacen.removeIf(tarea -> tarea.getId().equals(id));
        }

        @Override
        public Tarea buscarPorId(String id) {
            return almacen.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        }

        @Override
        public List<Tarea> obtenerProximas() {
            List<Tarea> proximas = new ArrayList<>();
            for (Tarea t : almacen) {
                if (t.esProxima() && !t.isCompletada()) {
                    proximas.add(t);
                }
            }
            return proximas;
        }

        @Override
        public List<Tarea> obtenerVencidas() {
            List<Tarea> vencidas = new ArrayList<>();
            for (Tarea t : almacen) {
                if (t.estaVencida() && !t.isCompletada()) {
                    vencidas.add(t);
                }
            }
            return vencidas;
        }
    }
}
