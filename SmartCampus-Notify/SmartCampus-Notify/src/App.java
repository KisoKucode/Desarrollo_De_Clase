import controller.CorreoController;
import controller.NotificacionController;
import controller.TareaController;
import repository.CorreoRepositoryJson;
import repository.TareaRepositoryJson;
import service.CorreoService;
import service.NotificacionService;
import service.TareaService;
import view.SmartCampusFrame;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CorreoController correoController = new CorreoController(new CorreoService(new CorreoRepositoryJson()));
            TareaController tareaController = new TareaController(new TareaService(new TareaRepositoryJson()));
            NotificacionController notificacionController = new NotificacionController(new NotificacionService(new TareaService(new TareaRepositoryJson())));
            
            SmartCampusFrame frame = new SmartCampusFrame(correoController, tareaController, notificacionController);
            frame.setVisible(true);
        });
    }
}

