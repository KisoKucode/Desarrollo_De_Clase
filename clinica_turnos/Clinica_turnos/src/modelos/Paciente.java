package modelos;

import enums.TipoPaciente;
import java.time.LocalTime;

public class Paciente {
    private String id;
    private String ticket;
    private LocalTime horaSolicitud;
    private boolean atendido;
    private TipoPaciente tipo;

    public Paciente(String id, String ticket, TipoPaciente tipo) {
        this.id = id;
        this.ticket = ticket;
        this.tipo = tipo;
        this.horaSolicitud = LocalTime.now();
        this.atendido = false;
    }

    public String getTicket() {
        return ticket;
    }

    public void actualizarEstado(boolean estado) {
        this.atendido = estado;
    }

    public String getId() {
        return id;
    }

    public TipoPaciente getTipo() {
        return tipo;
    }

    public LocalTime getHoraSolicitud() {
        return horaSolicitud;
    }

    public boolean isAtendido() {
        return atendido;
    }
}
