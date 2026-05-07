
## 2. Principio incumplido: ssInversión de Dependencias (DIP)
(Es la "D" de los principios SOLID).

Argumento (La razón):
El método calcular() está rompiendo el principio porque depende directamente de una clase concreta (Factura) en lugar de una abstracción (una interfaz o clase abstracta).

Al escribir Factura factura = new Factura(); dentro del método, estás creando un acoplamiento fuerte. Esto genera varios problemas:

Rigidez: Si en el futuro quieres usar una FacturaElectronica o una FacturaEspecial, tendrías que modificar el código de la función calcular.

Dificultad para testear: No puedes probar el método calcular de forma aislada (con un "mock") porque la factura está "pegada" (hardcoded) dentro de la lógica.

¿Cómo se debería arreglar? (Para que lo menciones en tu argumento)
Lo ideal sería aplicar Inyección de Dependencias. En lugar de crear la factura adentro, el método debería recibirla como parámetro o a través del constructor:

Java
// Forma correcta: el método no sabe CÓMO se crea la factura, solo la usa.
int calcular(IFactura factura) { 
    int resultado = factura.obtenerTotal();
    return aplicarIva(resultado);
}


### 3. Patrón de Diseño GoF: Abstract Factory
**Respuesta:** El patrón utilizado para crear objetos sin exponer la lógica de creación al cliente es el **Abstract Factory** (o Factory Method).

**Justificación:** Estos patrones proporcionan una interfaz para crear familias de objetos relacionados o dependientes sin especificar sus clases concretas. De esta manera, el cliente solo conoce la interfaz y no cómo se instancia o se configura el objeto internamente, garantizando el desacoplamiento total.

### 4. Principio SOLID: Sustitución de Liskov (LSP)

**Respuesta:** El principio que se está incumpliendo es el **Principio de Sustitución de Liskov (LSP)**.

**Razón:** Este principio establece que los objetos de una subclase deben poder reemplazar a los objetos de la clase base sin afectar la integridad del programa. Al implementar un método vacío, la subclase deja de ser un sustituto funcional de la clase padre, ya que rompe el comportamiento esperado por el cliente y altera la lógica del sistema.

### 5. Caso de Prueba JUnit (Validación de Excepción)

**Descripción:** Implementación de un test unitario para verificar que el método `dividir` de la clase `Calculadora` lanza una excepción de tipo aritmética cuando el divisor es cero.

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculadoraTest {

    @Test
    public void testDividirPorCeroDebeLanzarExcepcion() {
        // Arrange: Preparación del escenario
        Calculadora calculadora = new Calculadora();
        int dividendo = 10;
        int divisor = 0;

        // Act & Assert: Ejecución y validación de la excepción
        assertThrows(ArithmeticException.class, () -> {
            calculadora.dividir(dividendo, divisor);
        }, "Debería lanzar ArithmeticException al dividir por cero");
    }
}