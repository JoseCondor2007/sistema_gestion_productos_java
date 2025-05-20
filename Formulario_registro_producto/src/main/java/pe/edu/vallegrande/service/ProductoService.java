package pe.edu.vallegrande.service;

import pe.edu.vallegrande.model.ProductoDAO;
import pe.edu.vallegrande.model.Producto;
import java.sql.SQLException;
import java.util.List;

public class ProductoService {

    private final ProductoDAO productoDAO = new ProductoDAO();

    public List<Producto> obtenerTodosProductos() throws SQLException{
        return productoDAO.listarProductos();
    }

    public void guardarProducto(Producto producto) throws SQLException {
        productoDAO.guardarProducto(producto);
    }

    public Producto obtenerProductoPorId(int id) throws SQLException{
        return productoDAO.obtenerProductoPorId(id);
    }

    public void actualizarProducto(Producto producto) throws SQLException{
        productoDAO.actualizarProducto(producto);
    }

    public void eliminarProducto(int id) throws SQLException {
        productoDAO.eliminarProducto(id);
    }
}