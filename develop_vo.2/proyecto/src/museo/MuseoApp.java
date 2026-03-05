package museo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import museo.models.*;
import museo.servicios.*;

public class MuseoApp extends JFrame {
    private CatalogoService catalogo = new CatalogoService();
    private RestauracionService restSvc = new RestauracionService();
    private CesionService cesionSvc = new CesionService();
    private AuthService auth = new AuthService();

    private DefaultTableModel catalogTableModel;
    private DefaultTableModel restauradorTableModel;

    public MuseoApp(){
        super("Museo - Demo");
        // datos demo
        Date hoy = new Date();
        Calendar cal = Calendar.getInstance(); cal.add(Calendar.YEAR,-6);
        Cuadro c1 = new Cuadro("O1","Autor A","Renac.",1000.0,cal.getTime(),hoy,"Sala 1","Estilo X","Óleo");
        Escultura e1 = new Escultura("O2","Autor B","Moderno",2000.0,cal.getTime(),hoy,"Sala 2","Estilo Y","Mármol");
        catalogo.agregarObra(c1); catalogo.agregarObra(e1);

        // GUI básico: pestañas para roles
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Visitante", createVisitantePanel());
        tabs.addTab("Encargado", createEncargadoPanel());
        tabs.addTab("Restaurador", createRestauradorPanel());
        tabs.addTab("Director", createDirectorPanel());

        getContentPane().add(tabs, BorderLayout.CENTER);
        setSize(800,600); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createVisitantePanel(){
        JPanel p = new JPanel(new BorderLayout());
        catalogTableModel = new DefaultTableModel(new String[]{"ID","Autor","Sala","Estado"},0){ @Override public boolean isCellEditable(int r,int c){return false;} };
        JTable table = new JTable(catalogTableModel);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField salaFilter = new JTextField(10);
        JButton btnFilter = new JButton("Filtrar por sala");
        btnFilter.addActionListener(e -> refreshCatalogTable(salaFilter.getText().trim()));
        JButton btnRefresh = new JButton("Mostrar todo"); btnRefresh.addActionListener(e-> refreshCatalogTable("") );
        top.add(new JLabel("Sala:")); top.add(salaFilter); top.add(btnFilter); top.add(btnRefresh);
        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        refreshCatalogTable("");
        return p;
    }

    private JPanel createEncargadoPanel(){
        JPanel p = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField idF = new JTextField(6);
        JTextField autorF = new JTextField(10);
        JTextField salaF = new JTextField(6);
        JTextField valorF = new JTextField(6);
        JComboBox<String> tipoCb = new JComboBox<>(new String[]{"Cuadro","Escultura","Otro"});
        JTextField extraF = new JTextField(8);
        JButton btnAdd = new JButton("Añadir obra");
        btnAdd.addActionListener(e->{
            String id = idF.getText().trim(); if(id.isEmpty()) id = "O"+(new Random().nextInt(10000));
            String autor = autorF.getText().trim(); String sala = salaF.getText().trim(); double val=0; try{val=Double.parseDouble(valorF.getText().trim());}catch(Exception ex){}
            String tipo = (String) tipoCb.getSelectedItem();
            Date ahora = new Date();
            if("Cuadro".equals(tipo)){
                Cuadro c = new Cuadro(id, autor, "-", val, ahora, ahora, sala, "-", extraF.getText().trim());
                catalogo.agregarObra(c);
            } else if("Escultura".equals(tipo)){
                Escultura s = new Escultura(id, autor, "-", val, ahora, ahora, sala, "-", extraF.getText().trim());
                catalogo.agregarObra(s);
            } else {
                OtroObjeto o = new OtroObjeto(id, autor, "-", val, ahora, ahora, sala);
                catalogo.agregarObra(o);
            }
            JOptionPane.showMessageDialog(this,"Obra añadida: " + id);
            refreshCatalogTable("");
        });
        form.add(new JLabel("ID")); form.add(idF);
        form.add(new JLabel("Autor")); form.add(autorF);
        form.add(new JLabel("Sala")); form.add(salaF);
        form.add(new JLabel("Valor")); form.add(valorF);
        form.add(tipoCb); form.add(new JLabel("Técnica/Material")); form.add(extraF); form.add(btnAdd);
        p.add(form, BorderLayout.NORTH);
        return p;
    }

    private JPanel createRestauradorPanel(){
        JPanel p = new JPanel(new BorderLayout());
        restauradorTableModel = new DefaultTableModel(new String[]{"ID","Estado","FechaEntrada"},0){ @Override public boolean isCellEditable(int r,int c){return false;} };
        JTable table = new JTable(restauradorTableModel);
        JButton btnProceso = new JButton("Ejecutar proceso diario");
        btnProceso.addActionListener(a->{
            List<Obra> para = restSvc.obrasParaRestaurar(catalogo.listar());
            for(Obra o: para) restSvc.iniciarRestauracion(o,"Automática",new Date());
            JOptionPane.showMessageDialog(this, para.size()+" obras enviadas a restauración (simulado)");
            refreshRestauradorTable(); refreshCatalogTable("");
        });
        JButton btnFinalizar = new JButton("Finalizar restauración");
        btnFinalizar.addActionListener(a->{
            int idx = table.getSelectedRow(); if(idx<0){ JOptionPane.showMessageDialog(this,"Selecciona una obra"); return; }
            String id = (String)restauradorTableModel.getValueAt(idx,0);
            Obra seleccion = catalogo.listar().stream().filter(o->o.getId().equals(id)).findFirst().orElse(null);
            if(seleccion!=null){ restSvc.finalizarRestauracion(seleccion,new Date()); JOptionPane.showMessageDialog(this,"Restauración finalizada: "+id); refreshRestauradorTable(); refreshCatalogTable(""); }
        });
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT)); controls.add(btnProceso); controls.add(btnFinalizar);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(controls, BorderLayout.SOUTH);
        refreshRestauradorTable();
        return p;
    }

    private JPanel createDirectorPanel(){
        JPanel p = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblValor = new JLabel("Valor total: " + catalogo.valorTotal());
        JButton btnRefresh = new JButton("Refrescar"); btnRefresh.addActionListener(e-> lblValor.setText("Valor total: " + catalogo.valorTotal()));
        top.add(lblValor); top.add(btnRefresh);
        p.add(top, BorderLayout.NORTH);
        return p;
    }

    private void refreshCatalogTable(String salaFilter){
        catalogTableModel.setRowCount(0);
        for(Obra o: catalogo.listar()){
            if(salaFilter!=null && !salaFilter.isEmpty() && !salaFilter.equals(o.getSala())) continue;
            catalogTableModel.addRow(new Object[]{o.getId(), o.getAutor(), o.getSala(), o.getEstado()});
        }
    }

    private void refreshRestauradorTable(){
        restauradorTableModel.setRowCount(0);
        for(Obra o: catalogo.listar()){
            restauradorTableModel.addRow(new Object[]{o.getId(), o.getEstado(), o.getFechaEntrada()});
        }
    }

    public static void main(String[] args){ SwingUtilities.invokeLater(()-> new MuseoApp().setVisible(true)); }
}
