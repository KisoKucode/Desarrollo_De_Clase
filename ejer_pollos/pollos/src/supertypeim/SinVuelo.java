package supertypeimp;
import supertype.TipoVuelo;

public class SinVuelo implements TipoVuelo {
    public void vuelo() {
        System.out.println("No puedo volar");
    }
}