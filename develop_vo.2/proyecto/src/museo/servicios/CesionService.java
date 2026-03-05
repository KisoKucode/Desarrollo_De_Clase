package museo.servicios;

import java.util.*;
import museo.models.Cesion;
import museo.models.Obra;

public class CesionService {
    private final Map<String, List<Cesion>> mapa = new HashMap<>();

    public void cederObra(Obra o, Cesion c){
        mapa.computeIfAbsent(o.getId(), k-> new ArrayList<>()).add(c);
        o.setEstado(museo.models.EstadoObra.CEDIDA);
    }

    public Optional<Cesion> siguienteCesionPendiente(Obra o){
        List<Cesion> lst = mapa.getOrDefault(o.getId(), Collections.emptyList());
        Date ahora = new Date();
        return lst.stream().filter(c->c.getInicio().after(ahora)).findFirst();
    }
}
