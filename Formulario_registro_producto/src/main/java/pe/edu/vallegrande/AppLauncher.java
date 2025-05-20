package pe.edu.vallegrande;

import javax.swing.SwingUtilities;
import pe.edu.vallegrande.view.RegistroProductoView;
import pe.edu.vallegrande.controller.ProductoController;

public class AppLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistroProductoView view = new RegistroProductoView();
            ProductoController controller = new ProductoController(view); // Pasar la vista al controlador
            view.setController(controller); // Establecer el controlador en la vista
            view.setVisible(true); // Mostrar la vista
        });
    }
}