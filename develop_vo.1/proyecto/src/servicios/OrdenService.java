package servicios;

import inventario.ISistemaInventario;
import java.util.ArrayList;
import java.util.List;
import modelos.ItemOrden;
import modelos.OrdenCompra;

public class OrdenService {
    private ISistemaInventario inventario;
    private pagos.MetodoPago metodoPago;

    public OrdenService(ISistemaInventario inventario, pagos.MetodoPago metodoPago) {
        this.inventario = inventario;
        this.metodoPago = metodoPago;
        this.ordenesConfirmadas = new ArrayList<>();
    }

    public void procesarOrden(OrdenCompra orden) {
        // verificar stock y actualizar
        boolean puede = true;
        for (ItemOrden it : orden.getItems()) {
            String codigo = it.getProducto().getCodigo();
            int stock = inventario.consultarStock(codigo);
            if (stock < it.getCantidad()) {
                puede = false;
                System.out.println("Stock insuficiente para producto: " + codigo);
            }
        }
        if (puede) {
            // actualizar stock
            for (ItemOrden it : orden.getItems()) {
                inventario.actualizarStock(it.getProducto().getCodigo(), -it.getCantidad());
            }
            // usar el metodo de pago de la orden si existe, sino usar el por defecto
            if (orden.getMetodoPago() == null && metodoPago != null) {
                // asignar metodo de servicio
                // no hay setter en OrdenCompra por UML, pero la clase tiene constructor con MetodoPago
            }
            orden.confirmar();
            // almacenar orden confirmada para que agentes la consulten
            ordenesConfirmadas.add(orden);
        } else {
            System.out.println("No se puede procesar la orden " + orden.getCodigo());
        }
    }

    private List<OrdenCompra> ordenesConfirmadas;

    public List<OrdenCompra> obtenerOrdenesConfirmadas() {
        return ordenesConfirmadas;
    }

    public void asignarTransportista(OrdenCompra orden, String transportista) {
        orden.asignarTransportista(transportista);
        System.out.println("Transportista " + transportista + " asignado a orden " + orden.getCodigo());
    }

    public void marcarEmpacado(OrdenCompra orden) {
        orden.marcarEmpacado();
        System.out.println("Orden " + orden.getCodigo() + " marcada como empacada.");
    }

    public void cancelarOrden(OrdenCompra orden) {
        orden.cancelar();
        ordenesConfirmadas.remove(orden);
        System.out.println("Orden " + orden.getCodigo() + " cancelada desde servicio.");
    }
}
