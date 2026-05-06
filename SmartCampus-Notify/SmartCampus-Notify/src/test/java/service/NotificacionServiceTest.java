package service;

import model.Notificacion;
import model.Notificacion.TipoNotificacion;
import model.Tarea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ITareaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionServiceTest {
    private NotificacionService servicio;
    private TareaService tareaService;

    @BeforeEach
    void setUp() {
        ITareaRepository repo = new InMemoryTareaRepository();
        tareaService = new TareaService(repo);
        servicio = new NotificacionService(tareaService);
    }

    @Test
    void generaNotificacionesDeTareasProximas() {
        tareaService.crearTarea("Examen próximo", "Matemáticas", LocalDate.now().plusDays(2), "Mate");
        servicio.generarNotificacionesAcademicas();
        
        List<Notificacion> notificaciones = servicio.obtenerNotificaciones();
        assertFalse(notificaciones.isEmpty());
        assertTrue(notificaciones.stream().anyMatch(n -> n.getTipo() == TipoNotificacion.TAREA_PROXIMA));
    }

    @Test
    void generaNotificacionesDeTareasVencidas() {
        tareaService.crearTarea("Tarea vencida", "Descripción", LocalDate.now().minusDays(5), "Materia");
        servicio.generarNotificacionesAcademicas();
        
        List<Notificacion> notificaciones = servicio.obtenerNotificaciones();
        assertTrue(notificaciones.stream().anyMatch(n -> n.getTipo() == TipoNotificacion.TAREA_VENCIDA));
    }

    @Test
    void marcarComoLeidaCambiaEstado() {
        tareaService.crearTarea("Tarea", "Desc", LocalDate.now().plusDays(1), "Materia");
        servicio.generarNotificacionesAcademicas();
        Notificacion notif = servicio.obtenerNotificaciones().get(0);
        
        assertFalse(notif.isLeida());
        servicio.marcarComoLeida(notif.getId());
        assertTrue(notif.isLeida());
    }

    @Test
    void contarNotificacionesNoLeidas() {
        tareaService.crearTarea("Tarea1", "Desc", LocalDate.now().plusDays(1), "Materia");
        tareaService.crearTarea("Tarea2", "Desc", LocalDate.now().plusDays(2), "Materia");
        servicio.generarNotificacionesAcademicas();
        
        assertEquals(2, servicio.obtenerNotificacionesNoLeidas().size());
        servicio.marcarComoLeida(servicio.obtenerNotificaciones().get(0).getId());
        assertEquals(1, servicio.obtenerNotificacionesNoLeidas().size());
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
