package model;

import util.JsonUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class Tarea {
    private static final DateTimeFormatter FECHA_FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaLimite;
    private String materia;
    private boolean completada;

    public Tarea(String titulo, String descripcion, LocalDate fechaLimite, String materia) {
        this.id = null;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.materia = materia;
        this.completada = false;
    }

    public Tarea(String id, String titulo, String descripcion, String fechaLimite, String materia, boolean completada) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite != null && !fechaLimite.isBlank()
            ? LocalDate.parse(fechaLimite, FECHA_FORMATO)
            : LocalDate.now();
        this.materia = materia;
        this.completada = completada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getMaterial() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public void asignarIdSiFalta() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public long diasRestantes() {
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), this.fechaLimite);
    }

    public boolean esProxima() {
        return diasRestantes() <= 3 && diasRestantes() >= 0;
    }

    public boolean estaVencida() {
        return diasRestantes() < 0;
    }

    public String toJson() {
        return JsonUtil.tareaToJson(this);
    }

    public static Tarea fromJson(String json) {
        return JsonUtil.parseTarea(json);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return Objects.equals(id, tarea.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
