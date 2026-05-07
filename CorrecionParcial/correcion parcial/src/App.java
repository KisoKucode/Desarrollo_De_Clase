import punto1.Estudiante;
import punto1.EstudianteTelescopico;

public class App {
    public static void main(String[] args) {
        EstudianteTelescopico estudianteTelescopico = new EstudianteTelescopico(
                "Ana",
                "Pérez",
                20,
                "Ingeniería de Sistemas",
                8.9,
                "ana.perez@uni.edu",
                "+34 600 123 456"
        );

        Estudiante estudianteBuilder = new Estudiante.Builder("Ana", "Pérez")
                .edad(20)
                .carrera("Ingeniería de Sistemas")
                .promedio(8.9)
                .correo("ana.perez@uni.edu")
                .telefono("+34 600 123 456")
                .build();

        System.out.println("=== Constructor telescópico ===");
        System.out.println(estudianteTelescopico);

        System.out.println("\n=== Patrón Builder ===");
        System.out.println(estudianteBuilder);
    }
}
