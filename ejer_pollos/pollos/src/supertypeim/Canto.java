package supertypeim;
import supertype.TipoSonido;

public class Canto implements TipoSonido {
    public void makeSound() {
        System.out.println("Estoy cantando");
    }
}