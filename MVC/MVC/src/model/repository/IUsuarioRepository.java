package model.repository;
import java.io.IOException;
import java.util.List;

import model.Usuario;

public interface IUsuarioRepository {
    void save(Usuario usuario) throws IOException;
    List<Usuario> findAll() throws IOException;
    List<Usuario> listar();
}
