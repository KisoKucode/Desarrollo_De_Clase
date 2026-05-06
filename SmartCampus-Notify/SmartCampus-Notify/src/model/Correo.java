package model;

import util.JsonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class Correo {
    private static final DateTimeFormatter FECHA_FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String id;
    private String destinatario;
    private String asunto;
    private String contenido;
    private String fecha;

    public Correo(String destinatario, String asunto, String contenido) {
        this.id = null;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.contenido = contenido;
        this.fecha = LocalDateTime.now().format(FECHA_FORMATO);
    }

    public Correo(String id, String destinatario, String asunto, String contenido, String fecha) {
        this.id = id;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.contenido = contenido;
        this.fecha = fecha == null || fecha.isBlank()
            ? LocalDateTime.now().format(FECHA_FORMATO)
            : fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void asignarIdSiFalta() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public String toJson() {
        return JsonUtil.correoToJson(this);
    }

    public static Correo fromJson(String json) {
        return JsonUtil.parseCorreo(json);
    }

    public boolean esDuplicadoDe(Correo otro) {
        if (otro == null) {
            return false;
        }
        return this.destinatario != null
            && this.asunto != null
            && this.destinatario.equalsIgnoreCase(otro.destinatario)
            && this.asunto.equalsIgnoreCase(otro.asunto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Correo correo = (Correo) o;
        return Objects.equals(id, correo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
