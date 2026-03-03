package servicios;

import enums.TipoPaciente;
import java.util.ArrayList;
import java.util.List;
import modelos.Paciente;

public class TurnoServiceImpl implements ITurnoService {
    private List<Paciente> repositorio = new ArrayList<>();
    private int contadorGlobal = 0;

    @Override
    public void registrarTurno(String id, TipoPaciente tipo) {
        contadorGlobal++;
        String ticket = tipo.prefijo + "-" + contadorGlobal;
        Paciente nuevo = new Paciente(id, ticket, tipo);
        repositorio.add(nuevo);
    }

    @Override
    public void cancelarTurno(String ticket) {
        repositorio.removeIf(p -> p.getTicket().equals(ticket));
    }

    @Override
    public List<Paciente> obtenerSiguientes() {
        return repositorio;
    }

    public void atenderPaciente() {
        if (!repositorio.isEmpty()) {
            Paciente p = repositorio.remove(0);
            p.actualizarEstado(true);
        }
    }
}
