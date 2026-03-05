package inventario;

public interface ISistemaInventario {
    int consultarStock(String codigo);
    void actualizarStock(String codigo, int cantidad);
}
