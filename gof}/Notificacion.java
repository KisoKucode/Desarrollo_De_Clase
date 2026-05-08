//Define una interfaz para crear un objeto, pero deja que las subclases decidan qué clase instanciar.

interface Notificacion { void enviar(String msj); }

class EmailNotificacion implements Notificacion {
    public void enviar(String msj) { System.out.println("Enviando Email: " + msj); }
}

abstract class NotificacionFactory {
    public abstract Notificacion crearNotificacion();
}

class EmailFactory extends NotificacionFactory {
    public Notificacion crearNotificacion() { return new EmailNotificacion(); }
}
