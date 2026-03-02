import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Paciente {
    private String id;
    private String ticket;
    private LocalTime hora;
    private boolean atendido;

    public Paciente(String id, TipoPaciente tipo, int numero) {
        this.id = id;
        this.hora = LocalTime.now();
        this.atendido = false;
        // Formato: Prefijo-Numero (Ej: M-102)
        this.ticket = tipo.prefijo + "-" + String.format("%03d", numero);
    }

    public String getTicket() { return ticket; }
    public String getId() { return id; }
    public String getHoraFormateada() {
        return hora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    public boolean isAtendido() { return atendido; }
    public void setAtendido(boolean atendido) { this.atendido = atendido; }
}