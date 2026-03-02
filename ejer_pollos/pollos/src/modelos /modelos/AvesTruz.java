package modelos;
import supertypeimp.SinVuelo;
import supertypeimp.SinCanto;

public class AvesTruz extends Ave {
    public AvesTruz() {
        miFormaDeVuelo = new SinVuelo();
        miFormaDeCanto = new SinCanto();
    }
}