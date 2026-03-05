package televentas.api;

import modelos.OrdenCompra;

public interface IOrdenService {
    void crearOrden(OrdenCompra orden);
    void confirmarOrden(String codigo);
    void cancelarOrden(String codigo);
}
