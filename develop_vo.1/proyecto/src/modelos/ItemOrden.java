package modelos;

public class ItemOrden {
    private int cantidad;
    private double precioUnitario;
    private Producto producto;

    public ItemOrden(Producto producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public Producto getProducto() { return producto; }
}
