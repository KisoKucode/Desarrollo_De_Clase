package servicios;

import inventario.ISistemaInventario;
import modelos.ItemOrden;
import modelos.OrdenCompra;

public class OrdenService {
    private ISistemaInventario inventario;
    private pagos.MetodoPago metodoPago;

    public OrdenService(ISistemaInventario inventario, pagos.MetodoPago metodoPago) {
        this.inventario = inventario;
        this.metodoPago = metodoPago;
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
        } else {
            System.out.println("No se puede procesar la orden " + orden.getCodigo());
        }
    }
}
