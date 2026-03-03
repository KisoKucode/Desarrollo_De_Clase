package modelos;
import supertypeim.SinVuelo;
import supertypeim.SinCanto;

public class AvesTruz extends Ave {
    public AvesTruz() {
        miFormaDeVuelo = new SinVuelo();
        miFormaDeCanto = new SinCanto();
    }
}