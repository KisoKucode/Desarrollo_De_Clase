package televentas.impl;

import modelos.OrdenCompra;
import servicios.OrdenService;
import televentas.api.IOrdenService;

public class OrdenServiceAdapter implements IOrdenService {
    private final OrdenService impl;

    public OrdenServiceAdapter(OrdenService impl) {
        this.impl = impl;
    }

    @Override
    public void crearOrden(OrdenCompra orden) {
        impl.procesarOrden(orden);
    }

    @Override
    public void confirmarOrden(String codigo) {
        // en esta demo OrdenService procesa y confirma la orden en procesarOrden
        System.out.println("confirmarOrden llamado para: " + codigo);
    }

    @Override
    public void cancelarOrden(String codigo) {
        // no hay método directo para cancelar por codigo en OrdenService; dejar demo
        System.out.println("cancelarOrden llamado para: " + codigo);
    }
}
