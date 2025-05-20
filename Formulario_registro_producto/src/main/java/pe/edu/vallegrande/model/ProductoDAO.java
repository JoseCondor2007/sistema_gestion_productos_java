package pe.edu.vallegrande.model;

import pe.edu.vallegrande.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.sql.Timestamp;

public class ProductoDAO {

    public List<Producto> listarProductos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String query = "SELECT id_producto, nombre, tipo, marca, precio, stock, hay_stock, fecha_registro FROM Producto";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getDouble("precio"),
                        rs.getBoolean("hay_stock"),
                        rs.getInt("stock"),
                        rs.getTimestamp("fecha_registro")
                );
                lista.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return lista;
    }

    public void guardarProducto(Producto producto) throws SQLException {
        String query = "INSERT INTO Producto (nombre, tipo, marca, precio, stock, hay_stock, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getTipo());
            pstmt.setString(3, producto.getMarca());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setInt(5, producto.getStock());
            pstmt.setBoolean(6, producto.isHayStock());
            pstmt.setTimestamp(7, new Timestamp(producto.getFechaRegistro().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Producto obtenerProductoPorId(int id) throws SQLException {
        String query = "SELECT id_producto, nombre, tipo, marca, precio, stock, hay_stock, fecha_registro FROM Producto WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Producto(
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

    public void actualizarProducto(Producto producto) throws SQLException {
        String query = "UPDATE Producto SET nombre = ?, tipo = ?, marca = ?, precio = ?, stock = ?, hay_stock = ?, fecha_registro = ? WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getTipo());
            pstmt.setString(3, producto.getMarca());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setInt(5, producto.getStock());
            pstmt.setBoolean(6, producto.isHayStock());
            pstmt.setTimestamp(7, new Timestamp(producto.getFechaRegistro().getTime()));
            pstmt.setInt(8, producto.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void eliminarProducto(int id) throws SQLException {
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