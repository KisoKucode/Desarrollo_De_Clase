package modelos;

import java.util.Date;

public class Queja {
    private String motivo;
    private Date fecha;

    public Queja(String motivo, Date fecha) {
        this.motivo = motivo;
        this.fecha = fecha;
    }

    public String getMotivo() { return motivo; }
    public Date getFecha() { return fecha; }
}
