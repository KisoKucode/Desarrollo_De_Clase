package servicios;

import java.util.ArrayList;
import java.util.List;
import modelos.Producto;

public class CatalogoService {
    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto p) { productos.add(p); }
    public List<Producto> getProductos() { return productos; }
}
