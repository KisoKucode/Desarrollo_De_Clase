package modelos;
import supertypeim.Canto;
import supertypeim.VueloConAlas;

public class Canario extends Ave {
    public Canario() {
        miFormaDeVuelo = new VueloConAlas();
        miFormaDeCanto = new Canto();
    }
}