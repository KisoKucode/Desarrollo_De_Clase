package museo.servicios;

import java.util.*;
import museo.models.EstadoObra;
import museo.models.Obra;
import museo.models.Restauracion;

public class RestauracionService {
    private final Map<String, List<Restauracion>> mapa = new HashMap<>();

    public void iniciarRestauracion(Obra o, String tipo, Date inicio){
        o.setEstado(EstadoObra.RESTAURACION);
        Restauracion r = new Restauracion(tipo,inicio);
        mapa.computeIfAbsent(o.getId(), k-> new ArrayList<>()).add(r);
    }

    public void finalizarRestauracion(Obra o, Date fin){
        List<Restauracion> lst = mapa.getOrDefault(o.getId(), new ArrayList<>());
        if(!lst.isEmpty()){
            Restauracion r = lst.get(lst.size()-1);
            r.setFin(fin);
            o.setEstado(EstadoObra.EXHIBIDO);
        }
    }

    public List<Restauracion> getRestauraciones(Obra o){
        List<Restauracion> l = mapa.getOrDefault(o.getId(), new ArrayList<>());
        l.sort(Comparator.comparing(Restauracion::getInicio));
        return l;
    }

    // proceso diario simulador: marcar obras que requieran restauracion cada 5 años
    public List<Obra> obrasParaRestaurar(List<Obra> todas){
        List<Obra> res = new ArrayList<>();
        Date ahora = new Date();
        for(Obra o: todas){
            long diff = ahora.getTime() - o.getFechaEntrada().getTime();
            long years = diff / (1000L*60*60*24*365);
            if(years >=5 && o.getEstado()==EstadoObra.EXHIBIDO) res.add(o);
        }
        return res;
    }
}
