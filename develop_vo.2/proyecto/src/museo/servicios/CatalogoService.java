package museo.servicios;

import java.util.ArrayList;
import java.util.List;
import museo.models.Obra;

public class CatalogoService {
    private final List<Obra> catalogo = new ArrayList<>();
    public void agregarObra(Obra o){ catalogo.add(o); }
    public List<Obra> listar(){ return new ArrayList<>(catalogo); }
    public List<Obra> listarPorSala(String sala){
        List<Obra> r = new ArrayList<>(); for(Obra o: catalogo) if(sala.equals(o.getSala())) r.add(o); return r;
    }
    public double valorTotal(){ double s=0; for(Obra o: catalogo) s+=o.getValor(); return s; }
}
