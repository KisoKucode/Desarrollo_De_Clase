package model;
public enum ProgramaAcademico {
    DERECHO("Derecho"),
    PSICOLOGIA("Psicología"),
    ING_INDUSTRIAL("Ingeniería Industrial"),
    ING_SISTEMAS("Ingeniería en Sistemas"),
    CIENCIA_DATOS("Ciencia de Datos");

    private final String nombre;

    ProgramaAcademico(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
