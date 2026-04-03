# Taller de Principios SOLID
**Autor:** Daniel Alejandro Amado Poveda
**Carrera:** Ingeniería de Software

Este repositorio contiene la resolución del taller de principios SOLID, aplicando refactorización de código para mejorar la mantenibilidad y escalabilidad del software.

---

## 1. Principio Abierto-Cerrado (OCP)
[cite_start]La clase `Figuras` original no cumple el OCP porque está acoplada a tipos específicos (`Cuadrado`, `Circulo`)[cite: 9, 10, 11]. [cite_start]Para agregar nuevas formas, se requeriría modificar la lógica interna[cite: 18, 31].

### Refactorización
```java
public abstract class Figura {
    public abstract void dibujar();
}

public class Cuadrado extends Figura {
    @Override
    public void dibujar() {
        System.out.println("Dibujando Cuadrado");
    }
}

public class Circulo extends Figura {
    @Override
    public void dibujar() {
        System.out.println("Dibujando Circulo");
    }
}

public class Figuras {
    private Vector<Figura> lista = new Vector<>();

    public void addFigura(Figura f) {
        lista.add(f);
    }

    public void dibujarTodas() {
        Enumeration<Figura> e = lista.elements();
        while (e.hasMoreElements()) {
            e.nextElement().dibujar();
        }
    }
}
2. Principio de Sustitución de Liskov (LSP)La clase Configuracion viola el LSP debido a que ConfiguracionHoraria lanza una excepción (mensaje de error) en un método que debería ser funcional (save), rompiendo la jerarquía.RefactorizaciónSe segregan las interfaces para que los objetos solo implementen comportamientos que realmente soportan.Javapublic interface RecursoCargable {
    void load();
}

public interface RecursoPersistente extends RecursoCargable {
    void save();
}

public class ConfiguracionHoraria implements RecursoCargable {
    public void load() {
        System.out.println("Configuracion horaria cargada");
    }
}
3. Principio de Responsabilidad Única (SRP)La clase Factura original tiene múltiples razones para cambiar (cambio de IVA, lógica de deducción, etc.).RefactorizaciónJavapublic class CalculadoraDeduccion {
    public float calcular(float importe, int porcentaje) {
        if (importe > 10000) {
            return (importe * porcentaje + 3) / 100;
        }
        return (importe * porcentaje) / 100;
    }
}

public class CalculadoraIVA {
    public float calcular(float importe, String codigo) {
        if (codigo.equals("0")) {
            return 0;
        }
        return (float) (importe * 0.18);
    }
}
4. Principio de Inversión de Dependencia (DIP)El código original depende de implementaciones concretas mediante el uso de new dentro de la clase Factura. Para cumplir el DIP, se deben inyectar las interfaces correspondientes a través del constructor.5. Principio de Segregación de Interfaces (ISP)Las clases EmailSender y SMSSender dependen de la clase Contacto completa, recibiendo datos que no utilizan.RefactorizaciónJavapublic interface Emailable {
    String getEmailAddress();
}

public class Contacto implements Emailable {
    private String email;
    public String getEmailAddress() { return email; }
}

public class EmailSender {
    public static void sendEmail(Emailable c, String message) {
        System.out.println("Enviando a " + c.getEmailAddress());
    }
}