//Una "cara" simple para un sistema complejo de muchas clases.

class CPU { void encender() {} }
class Memoria { void cargar() {} }

public class ComputadoraFacade {
    private CPU cpu = new CPU();
    private Memoria memoria = new Memoria();

    public void arrancar() {
        cpu.encender();
        memoria.cargar();
        System.out.println("Sistema listo.");
    }
}