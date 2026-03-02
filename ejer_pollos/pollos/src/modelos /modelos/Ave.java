package modelos;
import supertype.TipoVuelo;
import supertype.TipoSonido;

public abstract class Ave {
    public TipoVuelo miFormaDeVuelo;
    public TipoSonido miFormaDeCanto;

    public void realizarVuelo() {
        miFormaDeVuelo.vuelo();
    }

    public void realizarSonido() {
        miFormaDeCanto.makeSound();
    }
}