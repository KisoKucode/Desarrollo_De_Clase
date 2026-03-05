package modelos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pagos.MetodoPago;

public class OrdenCompra {
    private String codigo;
    private Date fecha;
    private EstadoOrden estado;
    private List<ItemOrden> items = new ArrayList<>();
    private MetodoPago metodoPago;
    private boolean empacado = false;
    private String transportista = null;

    public OrdenCompra(String codigo, Date fecha, MetodoPago metodoPago) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.estado = EstadoOrden.PENDIENTE;
        this.metodoPago = metodoPago;
    }

    public String getCodigo() { return codigo; }
    public Date getFecha() { return fecha; }
    public EstadoOrden getEstado() { return estado; }
    public List<ItemOrden> getItems() { return items; }
    public MetodoPago getMetodoPago() { return metodoPago; }

    public void agregarItem(ItemOrden item) { items.add(item); }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemOrden it : items) {
            total += it.getCantidad() * it.getPrecioUnitario();
        }
        return total;
    }

    public void confirmar() {
        if (estado == EstadoOrden.PENDIENTE) {
            double total = calcularTotal();
            if (metodoPago != null) {
                metodoPago.procesarPago(total);
            }
            estado = EstadoOrden.CONFIRMADA;
            System.out.println("Orden " + codigo + " confirmada. Total: " + total);
        }
    }

    public void cancelar() {
        if (estado != EstadoOrden.CANCELADA) {
            estado = EstadoOrden.CANCELADA;
            System.out.println("Orden " + codigo + " cancelada.");
        }
    }

    public boolean isEmpacado() { return empacado; }
    public void marcarEmpacado() { this.empacado = true; }

    public String getTransportista() { return transportista; }
    public void asignarTransportista(String transportista) { this.transportista = transportista; }
}
