package supertypeimp;
import supertype.TipoSonido;

public class SinCanto implements TipoSonido {
    public void makeSound() {
        System.out.println("<< Silencio >>");
    }
}