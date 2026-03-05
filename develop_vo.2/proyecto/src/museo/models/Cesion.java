package museo.models;

import java.util.Date;

public class Cesion {
    private String museoDestino;
    private Date inicio, fin;
    private double importe;

    public Cesion(String museoDestino, Date inicio, Date fin, double importe){
        this.museoDestino = museoDestino; this.inicio = inicio; this.fin = fin; this.importe = importe;
    }
    public Date getFin(){return fin;} public Date getInicio(){return inicio;} public double getImporte(){return importe;}
}
