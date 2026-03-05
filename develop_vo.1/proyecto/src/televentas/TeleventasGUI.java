package televentas;

import inventario.InMemoryInventario;
import java.awt.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelos.ItemOrden;
import modelos.OrdenCompra;
import modelos.Producto;
import pagos.PagoTarjetaCredito;
import servicios.CatalogoService;
import servicios.OrdenService;
import televentas.api.IOrdenService;
import televentas.api.IPago;
import televentas.impl.OrdenServiceAdapter;
import televentas.impl.PagoTarjetaAdapter;

public class TeleventasGUI extends JFrame {
    private CatalogoService catalogo;
    private InMemoryInventario inventario;
    private IOrdenService ordenService;
    private IPago pago;
    private DefaultTableModel tablaModel;

    public TeleventasGUI() {
        super("Televentas - Demo");
        catalogo = new CatalogoService();
        inventario = new InMemoryInventario();

        // datos demo
        Producto p1 = new Producto("P001","Producto A",10.0);
        Producto p2 = new Producto("P002","Producto B",20.0);
        catalogo.agregarProducto(p1);
        catalogo.agregarProducto(p2);
        inventario.setStock(p1.getCodigo(), 5);
        inventario.setStock(p2.getCodigo(), 3);

        // servicios e adapters
        OrdenService ordenSvc = new OrdenService(inventario, null);
        ordenService = new OrdenServiceAdapter(ordenSvc);
        // keep concrete service reference for agent actions
        OrdenService ordenServiceConcreto = ordenSvc;
        PagoTarjetaCredito tarjeta = new PagoTarjetaCredito("0000-1111-2222-3333");
        pago = new PagoTarjetaAdapter(tarjeta);

        tablaModel = new DefaultTableModel(new String[]{"Código","Descripción","Precio","Stock"},0){
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable tabla = new JTable(tablaModel);
        refreshTabla();

        // Cliente panel
        JPanel clientePanel = new JPanel(new BorderLayout());
        JTextField cantidadField = new JTextField(3);
        JButton btnCrear = new JButton("Crear y Pagar Orden");
        btnCrear.addActionListener(e -> {
            int idx = tabla.getSelectedRow();
            if (idx < 0) { JOptionPane.showMessageDialog(this, "Selecciona un producto"); return; }
            String codigo = (String) tablaModel.getValueAt(idx,0);
            Producto prod = catalogo.getProductos().stream().filter(p->p.getCodigo().equals(codigo)).findFirst().orElse(null);
            int cant = 1;
            try { cant = Integer.parseInt(cantidadField.getText().trim()); } catch(Exception ex) {}
            if (prod==null) return;
            OrdenCompra orden = new OrdenCompra("TV-"+System.currentTimeMillis(), new Date(), null);
            orden.agregarItem(new ItemOrden(prod,cant,prod.getPrecio()));
            // crear y procesar
            ordenService.crearOrden(orden);
            pago.procesarPago(orden.calcularTotal());
            refreshTabla();
            JOptionPane.showMessageDialog(this, "Orden creada y pagada: " + orden.getCodigo());
        });
        JPanel controls = new JPanel();
        controls.add(new JLabel("Cantidad:")); controls.add(cantidadField); controls.add(btnCrear);

        // actions: request catalog (simulated email)
        JButton btnEnviarCatalogo = new JButton("Enviar catálogo por email");
        btnEnviarCatalogo.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Catálogo enviado por correo (simulado)");
        });

        // complaints
        JButton btnQueja = new JButton("Presentar queja");
        btnQueja.addActionListener(e -> {
            String motivo = JOptionPane.showInputDialog(this, "Motivo de la queja:");
            if (motivo != null && !motivo.trim().isEmpty()) {
                servicios.QuejaService qs = new servicios.QuejaService(new usuarios.GerenteRelaciones("Gerente","g@empresa.com"));
                modelos.Queja queja = new modelos.Queja(motivo, new Date());
                usuarios.Cliente cli = new usuarios.Cliente("ClienteDemo","c@demo.com");
                cli.presentarQueja(qs, queja);
                JOptionPane.showMessageDialog(this, "Queja registrada (simulado)");
            }
        });

        JPanel topControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topControls.add(btnEnviarCatalogo);
        topControls.add(btnQueja);

        clientePanel.add(topControls, BorderLayout.NORTH);
        clientePanel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        clientePanel.add(controls, BorderLayout.SOUTH);

        // Agente panel
        JPanel agentePanel = new JPanel(new BorderLayout());
        DefaultTableModel agenteModel = new DefaultTableModel(new String[]{"Codigo","Total","Empacado","Transportista"},0) {
            @Override public boolean isCellEditable(int r,int c){return false;}
        };
        JTable agenteTabla = new JTable(agenteModel);
        JButton btnRefrescarAgente = new JButton("Refrescar órdenes confirmadas");
        btnRefrescarAgente.addActionListener(e -> {
            agenteModel.setRowCount(0);
            for (OrdenCompra oc : ordenSvc.obtenerOrdenesConfirmadas()) {
                agenteModel.addRow(new Object[]{oc.getCodigo(), oc.calcularTotal(), oc.isEmpacado(), oc.getTransportista()});
            }
        });

        JButton btnEmpacar = new JButton("Marcar Empacado");
        btnEmpacar.addActionListener(e -> {
            int idx = agenteTabla.getSelectedRow();
            if (idx < 0) { JOptionPane.showMessageDialog(this, "Selecciona una orden"); return; }
            String codigo = (String) agenteModel.getValueAt(idx, 0);
            OrdenCompra oc = ordenSvc.obtenerOrdenesConfirmadas().stream().filter(o->o.getCodigo().equals(codigo)).findFirst().orElse(null);
            if (oc != null) { ordenSvc.marcarEmpacado(oc); btnRefrescarAgente.doClick(); }
        });

        JButton btnAsignarTrans = new JButton("Asignar Transportista");
        btnAsignarTrans.addActionListener(e -> {
            int idx = agenteTabla.getSelectedRow();
            if (idx < 0) { JOptionPane.showMessageDialog(this, "Selecciona una orden"); return; }
            String codigo = (String) agenteModel.getValueAt(idx, 0);
            String t = JOptionPane.showInputDialog(this, "Nombre de transportista:");
            if (t != null && !t.trim().isEmpty()) {
                OrdenCompra oc = ordenSvc.obtenerOrdenesConfirmadas().stream().filter(o->o.getCodigo().equals(codigo)).findFirst().orElse(null);
                if (oc != null) { ordenSvc.asignarTransportista(oc, t); btnRefrescarAgente.doClick(); }
            }
        });

        JPanel agenteControls = new JPanel();
        agenteControls.add(btnRefrescarAgente); agenteControls.add(btnEmpacar); agenteControls.add(btnAsignarTrans);
        agentePanel.add(new JScrollPane(agenteTabla), BorderLayout.CENTER);
        agentePanel.add(agenteControls, BorderLayout.SOUTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Cliente", clientePanel);
        tabs.addTab("Agente", agentePanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabs, BorderLayout.CENTER);

        setSize(800,600); setLocationRelativeTo(null); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void refreshTabla(){
        tablaModel.setRowCount(0);
        for(Producto p: catalogo.getProductos()){
            tablaModel.addRow(new Object[]{p.getCodigo(), p.getDescripcion(), p.getPrecio(), inventario.consultarStock(p.getCodigo())});
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            TeleventasGUI g = new TeleventasGUI();
            g.setVisible(true);
        });
    }
}
