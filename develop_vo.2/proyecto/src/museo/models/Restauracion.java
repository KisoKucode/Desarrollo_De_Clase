package museo.models;

import java.util.Date;

public class Restauracion {
    private String tipo;
    private Date inicio;
    private Date fin;

    public Restauracion(String tipo, Date inicio){ this.tipo = tipo; this.inicio = inicio; }
    public void setFin(Date f){ this.fin = f; }
    public Date getInicio(){return inicio;} public Date getFin(){return fin;} public String getTipo(){return tipo;}
}
