package museo.models;

import java.util.Date;

public abstract class Obra {
    protected String id;
    protected String autor;
    protected String periodo;
    protected double valor;
    protected Date fechaCreacion;
    protected Date fechaEntrada;
    protected String sala;
    protected EstadoObra estado = EstadoObra.EXHIBIDO;

    public Obra(String id, String autor, String periodo, double valor, Date fechaCreacion, Date fechaEntrada, String sala) {
        this.id = id; this.autor = autor; this.periodo = periodo; this.valor = valor;
        this.fechaCreacion = fechaCreacion; this.fechaEntrada = fechaEntrada; this.sala = sala;
    }

    public String getId(){return id;}
    public String getAutor(){return autor;}
    public double getValor(){return valor;}
    public String getSala(){return sala;}
    public EstadoObra getEstado(){return estado;}
    public void setEstado(EstadoObra e){ this.estado = e; }
    public java.util.Date getFechaEntrada(){ return fechaEntrada; }
}
