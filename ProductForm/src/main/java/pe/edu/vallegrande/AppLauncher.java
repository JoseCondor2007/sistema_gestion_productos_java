package pe.edu.vallegrande;

import javax.swing.SwingUtilities;
import pe.edu.vallegrande.view.ProductFormView; // Renamed
import pe.edu.vallegrande.controller.ProductController;

public class AppLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductFormView view = new ProductFormView(); // Renamed
            ProductController controller = new ProductController(view);
            view.setController(controller);
            view.setVisible(true);
        });
    }
}