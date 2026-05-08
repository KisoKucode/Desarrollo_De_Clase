import controller.UsuarioController;
import model.repository.FileUsuarioRepository;
import view.UsuarioForm;

public class Main {
    public static void main(String[] args) {

        UsuarioForm view = new UsuarioForm();
        FileUsuarioRepository repo = new FileUsuarioRepository();

        new UsuarioController(view, repo);

        view.setVisible(true);
    }
}
