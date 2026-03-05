package inventario;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryInventario implements ISistemaInventario {
    private final Map<String, Integer> stock = new ConcurrentHashMap<>();

    public InMemoryInventario() {
    }

    public void setStock(String codigo, int cantidad) {
        stock.put(codigo, cantidad);
    }

    @Override
    public int consultarStock(String codigo) {
        return stock.getOrDefault(codigo, 0);
    }

    @Override
    public void actualizarStock(String codigo, int cantidad) {
        stock.merge(codigo, cantidad, Integer::sum);
    }
}
