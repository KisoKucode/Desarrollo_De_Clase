package museo.models;

import java.util.Date;

public class Escultura extends Obra {
    private String estilo;
    private String material;

    public Escultura(String id,String autor,String periodo,double valor,Date fechaCreacion,Date fechaEntrada,String sala,String estilo,String material){
        super(id,autor,periodo,valor,fechaCreacion,fechaEntrada,sala);
        this.estilo = estilo; this.material = material;
    }

    public String getMaterial(){return material;}
}
