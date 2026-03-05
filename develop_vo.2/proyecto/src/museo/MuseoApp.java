package museo;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;
import museo.models.*;
import museo.servicios.*;

public class MuseoApp extends JFrame {
    private CatalogoService catalogo = new CatalogoService();
    private RestauracionService restSvc = new RestauracionService();
    private CesionService cesionSvc = new CesionService();
    private AuthService auth = new AuthService();

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
        DefaultListModel<String> lm = new DefaultListModel<>();
        for(Obra o: catalogo.listar()) lm.addElement(o.getId()+" - "+o.getAutor()+" ("+o.getSala()+")");
        JList<String> list = new JList<>(lm);
        p.add(new JScrollPane(list), BorderLayout.CENTER);
        return p;
    }

    private JPanel createEncargadoPanel(){
        JPanel p = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        JButton btnAdd = new JButton("Añadir obra demo");
        btnAdd.addActionListener(e->{
            Cuadro c = new Cuadro("O"+(new Random().nextInt(1000)),"Nuevo","Contemporáneo",500.0,new Date(),new Date(),"Sala 3","EstiloZ","Acrílico");
            catalogo.agregarObra(c);
            JOptionPane.showMessageDialog(this,"Obra añadida: " + c.getId());
        });
        top.add(btnAdd); p.add(top, BorderLayout.NORTH);
        return p;
    }

    private JPanel createRestauradorPanel(){
        JPanel p = new JPanel(new BorderLayout());
        DefaultListModel<String> lm = new DefaultListModel<>();
        for(Obra o: catalogo.listar()) lm.addElement(o.getId()+" - " + o.getEstado());
        JList<String> list = new JList<>(lm);
        JButton btnProceso = new JButton("Ejecutar proceso diario");
        btnProceso.addActionListener(a->{
            List<Obra> para = restSvc.obrasParaRestaurar(catalogo.listar());
            for(Obra o: para) restSvc.iniciarRestauracion(o,"Automática",new Date());
            JOptionPane.showMessageDialog(this, para.size()+" obras enviadas a restauración (simulado)");
        });
        p.add(new JScrollPane(list), BorderLayout.CENTER);
        p.add(btnProceso, BorderLayout.SOUTH);
        return p;
    }

    private JPanel createDirectorPanel(){
        JPanel p = new JPanel(new BorderLayout());
        JButton btnValor = new JButton("Ver valoración total");
        btnValor.addActionListener(e->{ JOptionPane.showMessageDialog(this, "Valor total: " + catalogo.valorTotal()); });
        p.add(btnValor, BorderLayout.NORTH);
        return p;
    }

    public static void main(String[] args){ SwingUtilities.invokeLater(()-> new MuseoApp().setVisible(true)); }
}
