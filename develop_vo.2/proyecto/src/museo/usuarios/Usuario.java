package museo.usuarios;

public abstract class Usuario {
    protected String nombre; protected String correo;
    public Usuario(String nombre, String correo){ this.nombre = nombre; this.correo = correo; }
    public String getNombre(){return nombre;}
}
