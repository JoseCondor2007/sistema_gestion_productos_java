package pe.edu.vallegrande.service;

import pe.edu.vallegrande.model.ProductDAO; // Renamed
import pe.edu.vallegrande.model.Product; // Renamed
import java.sql.SQLException;
import java.util.List;

public class ProductService { // Renamed

    private final ProductDAO productDAO = new ProductDAO(); // Renamed

    public List<Product> getAllProducts() throws SQLException{ // Renamed method and Product class
        return productDAO.listProducts(); // Renamed
    }

    public void saveProduct(Product product) throws SQLException { // Renamed method and Product class
        productDAO.saveProduct(product); // Renamed
    }

    public Product getProductById(int id) throws SQLException{ // Renamed method and Product class
        return productDAO.getProductById(id); // Renamed
    }

    public void updateProduct(Product product) throws SQLException{ // Renamed method and Product class
        productDAO.updateProduct(product); // Renamed
    }

    public void deleteProduct(int id) throws SQLException { // Renamed method
        productDAO.deleteProduct(id); // Renamed
    }
}