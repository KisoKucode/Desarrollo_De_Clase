import inventario.InMemoryInventario;
import java.util.Date;
import modelos.ItemOrden;
import modelos.OrdenCompra;
import modelos.Producto;
import modelos.Queja;
import pagos.PagoTarjetaCredito;
import servicios.CatalogoService;
import servicios.OrdenService;
import servicios.QuejaService;
import usuarios.Cliente;
import usuarios.GerenteRelaciones;

public class App {
    public static void main(String[] args) throws Exception {
        // Preparar inventario en memoria
        InMemoryInventario inventario = new InMemoryInventario();
        // crear catálogo y productos
        CatalogoService catalogo = new CatalogoService();
        Producto prodA = new Producto("P001", "Producto A", 10.0);
        Producto prodB = new Producto("P002", "Producto B", 20.0);
        catalogo.agregarProducto(prodA);
        catalogo.agregarProducto(prodB);

        // cargar stock inicial
        inventario.setStock(prodA.getCodigo(), 5);
        inventario.setStock(prodB.getCodigo(), 2);

        // preparar método de pago
        PagoTarjetaCredito tarjeta = new PagoTarjetaCredito("4111-1111-1111-1111");

        // crear orden
        OrdenCompra orden = new OrdenCompra("O-1001", new Date(), tarjeta);
        orden.agregarItem(new ItemOrden(prodA, 2, prodA.getPrecio()));
        orden.agregarItem(new ItemOrden(prodB, 1, prodB.getPrecio()));

        // servicio de orden
        OrdenService ordenService = new OrdenService(inventario, null);
        ordenService.procesarOrden(orden);

        // crear cliente y asociar orden
        Cliente cliente = new Cliente("Juan Perez", "juan@example.com");
        cliente.agregarOrden(orden);

        // presentar una queja
        GerenteRelaciones gerente = new GerenteRelaciones("Maria Gerente", "mg@bank.com");
        QuejaService quejaService = new QuejaService(gerente);
        Queja q = new Queja("Producto dañado", new Date());
        cliente.presentarQueja(quejaService, q);

        System.out.println("Demo finalizada.");
    }
}
