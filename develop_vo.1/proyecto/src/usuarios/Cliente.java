package usuarios;

import java.util.ArrayList;
import java.util.List;
import modelos.OrdenCompra;
import modelos.Queja;
import servicios.OrdenService;
import servicios.QuejaService;

public class Cliente extends Usuario {
    private List<OrdenCompra> ordenes = new ArrayList<>();
    private List<Queja> quejas = new ArrayList<>();

    public Cliente() { super(); }

    public Cliente(String nombre, String email) { super(nombre, email); }

    public List<OrdenCompra> getOrdenes() { return ordenes; }

    public List<Queja> getQuejas() { return quejas; }

    public void agregarOrden(OrdenCompra orden) {
        ordenes.add(orden);
    }

    public void agregarQueja(Queja q) { quejas.add(q); }

    // Métodos de uso con servicios
    public void crearOrden(OrdenService ordenService, OrdenCompra orden) {
        ordenService.procesarOrden(orden);
        agregarOrden(orden);
    }

    public void presentarQueja(QuejaService quejaService, Queja q) {
        quejaService.registrarQueja(this, q);
        agregarQueja(q);
    }
}
