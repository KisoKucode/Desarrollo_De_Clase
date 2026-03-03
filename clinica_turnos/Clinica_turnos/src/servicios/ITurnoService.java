package servicios;

import enums.TipoPaciente;
import java.util.List;
import modelos.Paciente;

public interface ITurnoService {
    void registrarTurno(String id, TipoPaciente tipo);
    void cancelarTurno(String ticket);
    List<Paciente> obtenerSiguientes();
}
