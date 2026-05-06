package repository;

import model.Correo;

import java.util.List;

public interface ICorreoRepository {
    List<Correo> obtenerTodos();
    Correo guardar(Correo correo);
    void actualizar(Correo correo);
    void eliminar(String id);
    Correo buscarPorId(String id);
    boolean existePorDestinatarioYAsunto(String destinatario, String asunto, String idExcluido);
}
