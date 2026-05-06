package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class Notificacion {
    private static final DateTimeFormatter FECHA_FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public enum TipoNotificacion {
        TAREA_PROXIMA("Tarea próxima"),
        TAREA_VENCIDA("Tarea vencida"),
        CORREO_RECIBIDO("Correo recibido"),
        RECORDATORIO("Recordatorio");

        private final String descripcion;

        TipoNotificacion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    private String id;
    private String titulo;
    private String mensaje;
    private TipoNotificacion tipo;
    private String referencia;
    private LocalDateTime fechaCreacion;
    private boolean leida;

    public Notificacion(String titulo, String mensaje, TipoNotificacion tipo, String referencia) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.referencia = referencia;
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getFechaCreacionFormato() {
        return fechaCreacion.format(FECHA_FORMATO);
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notificacion that = (Notificacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
