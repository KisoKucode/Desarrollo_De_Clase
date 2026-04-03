# Taller de Principios SOLID
**Autor:** Daniel Alejandro Amado Poveda  
**Carrera:** Ingeniería de Software  
**Estado:** Junior Programmer

---

## 1. Principio Abierto-Cerrado (OCP)

### Diagnóstico
[cite_start]La clase `Figuras` no cumple con el principio OCP[cite: 1, 31]. [cite_start]Está acoplada a tipos específicos como `Cuadrado` y `Circulo`, lo que obliga a modificar la clase cada vez que se añade una nueva figura[cite: 5, 8, 9].

### Refactorización
[cite_start]Se utiliza polimorfismo para que la clase sea abierta a la extensión pero cerrada a la modificación[cite: 32].

```java
public abstract class Figura {
    public abstract void dibujar();
}

public class Cuadrado extends Figura {
    @Override
    public void dibujar() {
        System.out.println("Cuadrado");
    }
}

public class Circulo extends Figura {
    @Override
    public void dibujar() {
        System.out.println("Circulo");
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

2. Principio de Sustitución de Liskov (LSP)DiagnósticoLa clase Configuracion incumple el LSP porque ConfiguracionHoraria lanza un error en el método save(), rompiendo la sustituibilidad de la interfaz RecursoPersistente.RefactorizaciónSegregación de capacidades de carga y guardado.

public interface RecursoCargable {
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

3. Principio de Responsabilidad Única (SRP)DiagnósticoLa clase Factura asume responsabilidades de cálculo de impuestos y deducciones que deberían estar aisladas.Refactorización y Cambios SolicitadosDeducción dinámica: Se ajusta el cálculo si el importe supera 10000.IVA: Ajuste del 16% al 18%.Exención: Código de factura 0 no aplica IVA.

public class CalculadoraDeduccion {
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

4. Principio de Inversión de Dependencia (DIP)DiagnósticoLa clase Factura depende de implementaciones concretas mediante el uso de new, violando el DIP.SoluciónInyectar abstracciones (interfaces) a través del constructor para desacoplar las clases.

5. Principio de Segregación de Interfaces (ISP)DiagnósticoEmailSender y SMSSender dependen de la clase Contacto completa, accediendo a información que no necesitan para sus tareas específicas.RefactorizaciónCreación de interfaces específicas para cada servicio.

public interface Emailable {
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