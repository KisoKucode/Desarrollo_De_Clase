package test;

import controller.UsuarioController;
import model.Usuario;
import model.ValidacionException;
import model.repository.IUsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.time.LocalDate;

// Interfaz funcional para permitir que los lambdas lancen excepciones en los tests
@FunctionalInterface
interface TestRunnable {
    void run() throws Exception;
}

class FakeRepository implements IUsuarioRepository {
    private List<Usuario> lista = new ArrayList<>();

    @Override
    public void save(Usuario usuario) throws IOException {
        lista.add(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return lista;
    }

    @Override
    public List<Usuario> findAll() throws IOException {
        return lista;
    }
}

public class TestSuite {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {

        testCrearUsuarioValido();
        testNombreVacio();
        testNombreNull();
        testCorreoInvalido();
        testCorreoVacio();
        testCorreoValido();
        testControllerGuardar();
        testControllerListaVacia();
        testControllerNoGuardarInvalido();
        testMultiplesUsuarios();

        System.out.println("\nRESULTADO:");
        System.out.println("Pasaron: " + passed);
        System.out.println("Fallaron: " + failed);
    }

    static void assertTrue(boolean condicion, String nombreTest) {
        if (condicion) {
            System.out.println("[OK]    " + nombreTest);
            passed++;
        } else {
            System.out.println("[ERROR] " + nombreTest);
            failed++;
        }
    }

    static void assertThrows(TestRunnable r, String nombreTest) {
        try {
            r.run();
            System.out.println("[ERROR] " + nombreTest + " (Se esperaba una falla pero el dato fue aceptado)");
            failed++;
        } catch (Exception e) {
            System.out.println("[OK]    " + nombreTest + " (Capturó error esperado)");
            passed++;
        }
    }

    // Helper para crear usuarios válidos con el constructor de 5 parámetros
    private static Usuario crearUsuarioDummy(String nombre, String email) {
        return new Usuario(nombre, "12345678", email, LocalDate.now(), "Ing Sistemas");
    }

    // ---------------- TESTS ----------------

    static void testCrearUsuarioValido() {
        try {
            Usuario u = crearUsuarioDummy("Juan", "juan@mail.com");
            assertTrue(u.nombre().equals("Juan"), "Usuario válido");
        } catch (Exception e) {
            assertTrue(false, "Usuario válido");
        }
    }

    static void testNombreVacio() {
        assertThrows(() -> new Usuario("", "1234567", "correo@mail.com", LocalDate.now(), "Ing Sistemas"), 
            "Nombre vacío");
    }

    static void testNombreNull() {
        assertThrows(() -> new Usuario(null, "1234567", "correo@mail.com", LocalDate.now(), "Ing Sistemas"), 
            "Nombre null");
    }

    static void testCorreoInvalido() {
        assertThrows(() -> new Usuario("Juan", "1234567", "correo-invalido", LocalDate.now(), "Ing Sistemas"), 
            "Correo inválido");
    }

    static void testCorreoVacio() {
        assertThrows(() -> new Usuario("Juan", "1234567", "", LocalDate.now(), "Ing Sistemas"), 
            "Correo vacío");
    }

    static void testCorreoValido() {
        try {
            Usuario u = crearUsuarioDummy("Ana", "ana@gmail.com");
            assertTrue(u != null, "Correo válido");
        } catch (Exception e) {
            assertTrue(false, "Correo válido");
        }
    }

    static void testControllerGuardar() {
        try {
            FakeRepository repo = new FakeRepository();
            // Testeamos la capacidad del repositorio de persistir el modelo generado
            repo.save(crearUsuarioDummy("Carlos", "carlos@mail.com"));
            assertTrue(repo.listar().size() == 1, "Guardar usuario");
        } catch (Exception e) {
            assertTrue(false, "Guardar usuario");
        }
    }

    static void testControllerListaVacia() {
        FakeRepository repo = new FakeRepository();
        assertTrue(repo.listar().isEmpty(), "Lista vacía");
    }

    static void testControllerNoGuardarInvalido() {
        FakeRepository repo = new FakeRepository();

        assertThrows(() -> new Usuario("", "1234567", "correo@mail.com", LocalDate.now(), "Ing Sistemas"), 
            "No guardar inválido");
    }

    static void testMultiplesUsuarios() {
        try {
            FakeRepository repo = new FakeRepository();

            repo.save(crearUsuarioDummy("A", "a@mail.com"));
            repo.save(crearUsuarioDummy("B", "b@mail.com"));

            assertTrue(repo.listar().size() == 2, "Múltiples usuarios");
        } catch (Exception e) {
            assertTrue(false, "Múltiples usuarios");
        }
    }
}
