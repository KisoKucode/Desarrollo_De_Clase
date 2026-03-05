package museo.models;

import java.util.Date;

public class Cuadro extends Obra {
    private String estilo;
    private String tecnica;

    public Cuadro(String id,String autor,String periodo,double valor,Date fechaCreacion,Date fechaEntrada,String sala,String estilo,String tecnica){
        super(id,autor,periodo,valor,fechaCreacion,fechaEntrada,sala);
        this.estilo = estilo; this.tecnica = tecnica;
    }

    public String getTecnica(){return tecnica;}
}
