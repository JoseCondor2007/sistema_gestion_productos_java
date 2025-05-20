package pe.edu.vallegrande.controller;

import pe.edu.vallegrande.model.Producto;
import pe.edu.vallegrande.service.ProductoService;
import pe.edu.vallegrande.view.RegistroProductoView;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDate; // Recommended for modern date handling
import java.time.ZoneId; // For converting Date to LocalDate

public class ProductoController {

    private RegistroProductoView vista;
    private ProductoService productoService;
    // For handling dates from the view (input/display)
    // Using "dd-MM-yyyy" for display/input as per your request
    private SimpleDateFormat viewSdf = new SimpleDateFormat("dd-MM-yyyy");
    // For handling dates when saving/reading from the database
    // It's generally best to keep the internal date format consistent with SQL DATE/TIMESTAMP, which is YYYY-MM-DD
    // If your database column is just 'DATE', it will ignore the time component.
    // If it's 'TIMESTAMP', it will store date and time.
    // For internal consistency with java.sql.Timestamp, 'yyyy-MM-dd HH:mm:ss' is a good default.
    private SimpleDateFormat dbSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ProductoController(RegistroProductoView vista) {
        this.vista = vista;
        this.productoService = new ProductoService();
        this.vista.setController(this);
        cargarDatosEnTabla();
    }

    public void registrarProductoEnBaseDeDatos() {
        String nombre = vista.getTxtNombreProducto().getText().trim();
        String tipo = (String) vista.getCmbTipoProducto().getSelectedItem();
        String marca = vista.getTxtMarca().getText().trim();
        String precioStr = vista.getTxtPrecio().getText().trim();
        boolean hayStock = vista.getChkSiStock().isSelected();
        int stock = (int) vista.getSpnStock().getValue();
        String fechaRegistroStr = vista.getTxtFechaRegistro(); // Assumed to be in "dd-MM-yyyy" format from view

        // --- Validaciones ---
        if (nombre.isEmpty() || marca.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (stock < 0) {
            JOptionPane.showMessageDialog(vista, "El stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp fechaRegistro = null;
        if (fechaRegistroStr != null && !fechaRegistroStr.isEmpty()) {
            try {
                // Parse the date from the view's "dd-MM-yyyy" format
                Date parsedDate = viewSdf.parse(fechaRegistroStr);
                // Convert to Timestamp, typically setting time to midnight for a 'DATE' column
                fechaRegistro = new Timestamp(parsedDate.getTime());
                // If you explicitly need to ensure midnight (00:00:00), which is good for DATE columns:
                // LocalDate localDate = parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                // fechaRegistro = Timestamp.valueOf(localDate.atStartOfDay());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(vista, "El formato de la fecha de registro no es válido (DD-MM-YYYY).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            // If no date is entered, use the current date at midnight
            LocalDate ahora = LocalDate.now();
            fechaRegistro = Timestamp.valueOf(ahora.atStartOfDay());
        }

        Producto producto = new Producto(0, nombre, tipo, marca, precio, hayStock, stock, fechaRegistro);
        try {
            productoService.guardarProducto(producto);
            JOptionPane.showMessageDialog(vista, "Producto registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarFormulario();
            cargarDatosEnTabla();
        } catch (SQLException e) {
            System.err.println("Error al registrar el producto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al registrar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarProductoEnBaseDeDatos() {
        int filaSeleccionada = vista.getTablaProductos().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idProducto = (int) vista.getTablaProductos().getValueAt(filaSeleccionada, 0);
        String nombre = vista.getTxtNombreProducto().getText().trim();
        String tipo = (String) vista.getCmbTipoProducto().getSelectedItem();
        String marca = vista.getTxtMarca().getText().trim();
        String precioStr = vista.getTxtPrecio().getText().trim();
        boolean hayStock = vista.getChkSiStock().isSelected();
        int stock = (int) vista.getSpnStock().getValue();
        String fechaRegistroStr = vista.getTxtFechaRegistro(); // Assumed to be in "dd-MM-yyyy" format from view

        // --- Validaciones (similar a registrarProductoEnBaseDeDatos) ---
        if (nombre.isEmpty() || marca.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (stock < 0) {
            JOptionPane.showMessageDialog(vista, "El stock no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp fechaRegistro = null;
        if (fechaRegistroStr != null && !fechaRegistroStr.isEmpty()) {
            try {
                Date parsedDate = viewSdf.parse(fechaRegistroStr);
                fechaRegistro = new Timestamp(parsedDate.getTime());
                // LocalDate localDate = parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                // fechaRegistro = Timestamp.valueOf(localDate.atStartOfDay());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(vista, "El formato de la fecha de registro no es válido (DD-MM-YYYY).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            // If no date is entered in the form, try to retain the existing date from the table
            try {
                String fechaExistenteTableStr = (String) vista.getTablaProductos().getValueAt(filaSeleccionada, 7);
                if (fechaExistenteTableStr != null && !fechaExistenteTableStr.isEmpty()) {
                    // Parse from the "dd-MM-yyyy" format displayed in the table
                    Date parsedDate = viewSdf.parse(fechaExistenteTableStr);
                    fechaRegistro = new Timestamp(parsedDate.getTime());
                    // LocalDate localDate = parsedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    // fechaRegistro = Timestamp.valueOf(localDate.atStartOfDay());
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(vista, "Error al obtener o parsear la fecha existente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                // Handle cases where the column might be missing or value is null
                JOptionPane.showMessageDialog(vista, "No se pudo recuperar la fecha existente del producto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        Producto producto = new Producto(idProducto, nombre, tipo, marca, precio, hayStock, stock, fechaRegistro);
        try {
            productoService.actualizarProducto(producto);
            JOptionPane.showMessageDialog(vista, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarFormulario();
            cargarDatosEnTabla();
        } catch (SQLException e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al actualizar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarProducto() {
        int filaSeleccionada = vista.getTablaProductos().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idProducto = (int) vista.getTablaProductos().getValueAt(filaSeleccionada, 0);

        try {
            productoService.eliminarProducto(idProducto);
            JOptionPane.showMessageDialog(vista, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarFormulario();
            cargarDatosEnTabla();
        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al eliminar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarDatosEnTabla() {
        DefaultTableModel tableModel = vista.getTableModel();
        if (tableModel != null) {
            tableModel.setRowCount(0); // Clear existing data

            try {
                List<Producto> listaProductos = productoService.obtenerTodosProductos();
                if (listaProductos != null) {
                    for (Producto producto : listaProductos) {
                        String fechaRegistroStr = "";
                        if (producto.getFechaRegistro() != null) {
                            // Format the Timestamp from the database into "dd-MM-yyyy" for display
                            // dbSdf is used for internal handling, viewSdf for display.
                            // Ensure the Timestamp is correctly parsed to a Date first, then formatted.
                            try {
                                Date dateFromDb = new Date(producto.getFechaRegistro().getTime());
                                fechaRegistroStr = viewSdf.format(dateFromDb);
                            } catch (Exception e) { // Catch any parsing/formatting issues
                                System.err.println("Error al formatear la fecha para la tabla: " + e.getMessage());
                                // Fallback to a simple toString() if formatting fails
                                fechaRegistroStr = producto.getFechaRegistro().toString().split(" ")[0];
                            }
                        }
                        tableModel.addRow(new Object[]{
                                producto.getId(),
                                producto.getNombre(),
                                producto.getTipo(),
                                producto.getMarca(),
                                producto.getPrecio(),
                                producto.isHayStock() ? "Sí" : "No",
                                producto.getStock(),
                                fechaRegistroStr
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se pudieron obtener los productos de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                System.err.println("Error al cargar los productos: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Error: El modelo de la tabla es nulo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}