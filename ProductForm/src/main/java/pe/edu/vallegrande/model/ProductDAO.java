package pe.edu.vallegrande.model;

import pe.edu.vallegrande.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ProductDAO { // Renamed

    public List<Product> listProducts() throws SQLException { // Renamed method and Product class
        List<Product> list = new ArrayList<>(); // Renamed
        String query = "SELECT id_producto, nombre, tipo, marca, precio, stock, hay_stock, fecha_registro FROM Producto";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product( // Renamed
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getDouble("precio"),
                        rs.getBoolean("hay_stock"),
                        rs.getInt("stock"),
                        rs.getTimestamp("fecha_registro")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    public void saveProduct(Product product) throws SQLException { // Renamed method and Product class
        String query = "INSERT INTO Producto (nombre, tipo, marca, precio, stock, hay_stock, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, product.getName()); // Renamed
            pstmt.setString(2, product.getType()); // Renamed
            pstmt.setString(3, product.getBrand()); // Renamed
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getStock());
            pstmt.setBoolean(6, product.isHasStock()); // Renamed
            pstmt.setTimestamp(7, new Timestamp(product.getRegistrationDate().getTime())); // Renamed
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Product getProductById(int id) throws SQLException { // Renamed method and Product class
        String query = "SELECT id_producto, nombre, tipo, marca, precio, stock, hay_stock, fecha_registro FROM Producto WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Product( // Renamed
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getDouble("precio"),
                        rs.getBoolean("hay_stock"),
                        rs.getInt("stock"),
                        rs.getTimestamp("fecha_registro")
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateProduct(Product product) throws SQLException { // Renamed method and Product class
        String query = "UPDATE Producto SET nombre = ?, tipo = ?, marca = ?, precio = ?, stock = ?, hay_stock = ?, fecha_registro = ? WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, product.getName()); // Renamed
            pstmt.setString(2, product.getType()); // Renamed
            pstmt.setString(3, product.getBrand()); // Renamed
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getStock());
            pstmt.setBoolean(6, product.isHasStock()); // Renamed
            pstmt.setTimestamp(7, new Timestamp(product.getRegistrationDate().getTime())); // Renamed
            pstmt.setInt(8, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteProduct(int id) throws SQLException { // Renamed method
        String query = "DELETE FROM Producto WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}