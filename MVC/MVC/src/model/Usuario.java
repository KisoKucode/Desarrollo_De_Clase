package model;
import java.time.LocalDate;

public record Usuario(
        String nombre,
        String dni,
        String email,
        LocalDate fechaNacimiento,
        ProgramaAcademico programa
) {

    public Usuario(String nombre, String dni, String email, LocalDate fechaNacimiento, String programa) {
        this(validarNombre(nombre), dni, validarEmail(email), fechaNacimiento, ProgramaAcademico.valueOf(programa.toUpperCase().replace(" ", "_")));
    }

    private static String validarNombre(String n) {
        if (n == null || n.isBlank()) throw new IllegalArgumentException("Nombre obligatorio");
        return n;
    }

    private static String validarEmail(String e) {
        if (e == null || !e.contains("@")) throw new IllegalArgumentException("Email inválido");
        return e;
    }
}