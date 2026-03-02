package modelos;
import supertypeim.Canto;
import supertypeimp.VueloConAlas;

public class Canario extends Ave {
    public Canario() {
        miFormaDeVuelo = new VueloConAlas();
        miFormaDeCanto = new Canto();
    }
}