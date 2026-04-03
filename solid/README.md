----
# Resolución Taller: Principios SOLID
**Estudiante:** Daniel Alejandro Amado Poveda
**Carrera:** Ingeniería de Software

Este repositorio contiene la resolución detallada de los ejercicios propuestos en el taller de principios SOLID.

---

## 1. Principio Abierto-Cerrado (OCP)

**1. ¿Cumple la clase Figuras el Principio OCP?**
No. La clase `Figuras` tiene vectores y métodos específicos para `Cuadrado` y `Circulo`. Si se desea añadir una nueva figura, es necesario modificar el código de la clase, lo cual viola la regla de estar cerrada para la modificación.

**2. Modificación del código:**
```java
public abstract class Figura {
    public abstract void dibujar();
}

public class Cuadrado extends Figura {
    @Override
    public void dibujar() { System.out.println("Dibujando Cuadrado"); }
}

public class Circulo extends Figura {
    @Override
    public void dibujar() { System.out.println("Dibujando Circulo"); }
}

public class Figuras {
    private Vector<Figura> lista = new Vector<>();
    public void addFigura(Figura f) { lista.add(f); }
    public void dibujarTodas() {
        for (Figura f : lista) { f.dibujar(); }
    }
}
```

**3. ¿Es una refactorización?**
Sí, porque se ha mejorado la estructura interna para facilitar la extensión mediante polimorfismo sin alterar el comportamiento de la aplicación.

---

## 2. Principio de Liskov (LSP)

**1. Programa principal:**
```java
public class Main {
    public static void main(String[] args) {
        Configuracion config = new Configuracion();
        config.cargarConfiguracion();
        config.salvarConfiguracion();
    }
}
```

**2. ¿Cumple OCP?** No, porque `cargarConfiguracion` instancia clases concretas directamente.
**3. ¿Cumple LSP?** No, porque `ConfiguracionHoraria` lanza una excepción en `save()`, rompiendo la funcionalidad esperada de su tipo base.

**4. Refactorización:**
```java
public interface RecursoCargable { void load(); }
public interface RecursoPersistente extends RecursoCargable { void save(); }

public class ConfiguracionHoraria implements RecursoCargable {
    public void load() { System.out.println("Cargando..."); }
}
```

**5. Problema y solución:** El problema es heredar métodos que no se pueden cumplir. La solución es segregar interfaces para que cada clase implemente solo lo que puede hacer.

---

## 3. Principio de Responsabilidad Única (SRP)

**Refactorización de la clase Factura:**
```java
public class CalculadoraDeduccion {
    public float calcular(float importe, int porcentaje) {
        if (importe > 10000) return (importe * porcentaje + 3) / 100;
        return (importe * porcentaje) / 100;
    }
}

public class CalculadoraIVA {
    private float tasa = 0.18f; // IVA actualizado al 18%
    public float calcular(float importe, String codigo) {
        if (codigo.equals("0")) return 0;
        return importe * tasa;
    }
}
```

---

## 4. Principio de Inversión de Dependencia (DIP)

**1. ¿Cumple el principio?** No, porque la clase `Factura` depende de implementaciones concretas (`new Deduccion()`) en lugar de abstracciones.
**2. Refactorización:** Se deben inyectar interfaces de `Deduccion` e `Iva` a través del constructor de la clase `Factura`.

---

## 5. Principio de Segregación de Interfaces (ISP)

**1. Análisis:** `EmailSender` y `SMSSender` incumplen el ISP al recibir el objeto `Contacto` completo cuando solo necesitan un atributo (email o teléfono).

**2. Refactorización:**
```java
public interface Emailable { String getEmailAddress(); }

public class Contacto implements Emailable {
    private String email;
    public String getEmailAddress() { return email; }
}

public class EmailSender {
    public static void sendEmail(Emailable c, String message) {
        System.out.println("Enviando a " + c.getEmailAddress());
    }
}
```

**3. Programa Principal para GmailAccount:**
```java
public class GmailAccount implements Emailable {
    String name, emailAddress;
    public String getEmailAddress() { return emailAddress; }
}

public class TestEmail {
    public static void main(String[] args) {
        GmailAccount acc = new GmailAccount();
        EmailSender.sendEmail(acc, "Mensaje de prueba");
    }
}
```
