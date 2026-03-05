package servicios;

import modelos.Queja;
import usuarios.Cliente;
import usuarios.GerenteRelaciones;

public class QuejaService {
    private GerenteRelaciones gerente;

    public QuejaService(GerenteRelaciones gerente) {
        this.gerente = gerente;
    }

    public void registrarQueja(Cliente cliente, Queja q) {
        // delegar al gerente
        System.out.println("Queja registrada de " + cliente.getNombre() + ": " + q.getMotivo());
        // en un sistema real, se notificaría al gerente
    }
}
