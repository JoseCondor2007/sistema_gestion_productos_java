package pe.edu.vallegrande.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import pe.edu.vallegrande.controller.ProductoController;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class RegistroProductoView extends JFrame implements ActionListener {

    // Componentes de la interfaz
    private JLabel lblNombreProducto, lblTipoProducto, lblMarca, lblPrecio,
            lblHayStock, lblStock, lblFechaRegistro;
    private JTextField txtNombreProducto, txtMarca, txtPrecio;
    private JComboBox<String> cmbTipoProducto;
    private JCheckBox chkSiStock, chkNoStock;
    private JSpinner spnStock;
    private JButton btnRegistrar, btnModificar, btnEliminar;
    private JTable tablaProductos;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPaneTabla;

    // Componente para la fecha (JDateChooser)
    private JDateChooser dateChooser;

    private ProductoController controller;

    public RegistroProductoView() {
        inicializarComponentes();
        configurarVentana();
        disponerComponentes();
        agregarListeners();
    }

    private void inicializarComponentes() {
        lblNombreProducto = new JLabel("Nombre del producto:");
        lblTipoProducto = new JLabel("Tipo de producto:");
        lblMarca = new JLabel("Marca:");
        lblPrecio = new JLabel("Precio:");
        lblHayStock = new JLabel("¿Hay en stock?");
        lblStock = new JLabel("Stock:");
        lblFechaRegistro = new JLabel("Fecha de Registro:");

        txtNombreProducto = new JTextField(30);
        txtMarca = new JTextField(30);
        txtPrecio = new JTextField(15);

        cmbTipoProducto = new JComboBox<>(new String[]{"Para motos", "Para carros", "Para bicicletas", "Para camiones"});
        cmbTipoProducto.setPreferredSize(new Dimension(200, cmbTipoProducto.getPreferredSize().height));

        chkSiStock = new JCheckBox("Sí");
        chkNoStock = new JCheckBox("No");
        ButtonGroup grupoStock = new ButtonGroup();
        grupoStock.add(chkSiStock);
        grupoStock.add(chkNoStock);
        chkNoStock.setSelected(true);

        spnStock = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        spnStock.setEnabled(false);
        spnStock.setPreferredSize(new Dimension(100, spnStock.getPreferredSize().height));

        btnRegistrar = new JButton("Registrar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        // Los nombres de las columnas son importantes y deben coincidir con el orden que luego se carga
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Tipo", "Marca", "Precio", "¿En Stock?", "Stock", "Fecha Registro"}, 0);
        tablaProductos = new JTable(tableModel);
        scrollPaneTabla = new JScrollPane(tablaProductos);
        scrollPaneTabla.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));

        // Inicializar JDateChooser
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(120, dateChooser.getPreferredSize().height));
        // Opcional: configurar el formato de visualización del JDateChooser (solo para mostrar en el calendario)
        // dateChooser.setDateFormatString("dd-MM-yyyy"); // Esto es solo visual, no afecta el getDate()

        // Cargar y asignar imágenes a los botones, escalándolas
        try {
            ImageIcon originalIconRegistrar = new ImageIcon(getClass().getResource("/img/registrar.png"));
            Image imageRegistrar = originalIconRegistrar.getImage();
            Image scaledImageRegistrar = imageRegistrar.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon iconRegistrar = new ImageIcon(scaledImageRegistrar);
            btnRegistrar.setIcon(iconRegistrar);

            ImageIcon originalIconModificar = new ImageIcon(getClass().getResource("/img/modificar.png"));
            Image imageModificar = originalIconModificar.getImage();
            Image scaledImageModificar = imageModificar.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon iconModificar = new ImageIcon(scaledImageModificar);
            btnModificar.setIcon(iconModificar);

            ImageIcon originalIconEliminar = new ImageIcon(getClass().getResource("/img/eliminar.png"));
            Image imageEliminar = originalIconEliminar.getImage();
            Image scaledImageEliminar = imageEliminar.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon iconEliminar = new ImageIcon(scaledImageEliminar);
            btnEliminar.setIcon(iconEliminar);

        } catch (Exception e) {
            System.err.println("Error al cargar o escalar las imágenes de los botones: " + e.getMessage());
            e.printStackTrace();
        }

        btnRegistrar.setBackground(new Color(144, 238, 144));
        btnModificar.setBackground(new Color(255, 255, 153));
        btnEliminar.setBackground(new Color(255, 153, 153));
    }

    private void configurarVentana() {
        setTitle("Registro y Lista de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        java.net.URL imgURL = getClass().getResource("/img/rueda.png");
        if (imgURL == null) {
            System.err.println("¡No se pudo encontrar la imagen: /img/rueda.png!");
        } else {
            ImageIcon icono = new ImageIcon(imgURL);
            setIconImage(icono.getImage());
        }
    }

    private void disponerComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panelRegistro = new JPanel(new GridBagLayout());
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registro de Producto"));
        GridBagConstraints gbcRegistro = new GridBagConstraints();
        gbcRegistro.insets = new Insets(5, 5, 5, 5);
        gbcRegistro.anchor = GridBagConstraints.WEST;
        gbcRegistro.fill = GridBagConstraints.HORIZONTAL;

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 0;
        panelRegistro.add(lblNombreProducto, gbcRegistro);
        gbcRegistro.gridx = 1;
        gbcRegistro.gridwidth = GridBagConstraints.REMAINDER;
        panelRegistro.add(txtNombreProducto, gbcRegistro);
        gbcRegistro.gridwidth = 1;

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 1;
        panelRegistro.add(lblTipoProducto, gbcRegistro);
        gbcRegistro.gridx = 1;
        gbcRegistro.weightx = 1.0;
        panelRegistro.add(cmbTipoProducto, gbcRegistro);
        gbcRegistro.weightx = 0.0;

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 2;
        panelRegistro.add(lblMarca, gbcRegistro);
        gbcRegistro.gridx = 1;
        gbcRegistro.gridwidth = GridBagConstraints.REMAINDER;
        panelRegistro.add(txtMarca, gbcRegistro);
        gbcRegistro.gridwidth = 1;

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 3;
        panelRegistro.add(lblPrecio, gbcRegistro);
        gbcRegistro.gridx = 1;
        gbcRegistro.gridwidth = GridBagConstraints.REMAINDER;
        panelRegistro.add(txtPrecio, gbcRegistro);
        gbcRegistro.gridwidth = 1;

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 4;
        panelRegistro.add(lblHayStock, gbcRegistro);
        JPanel panelStockCheck = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelStockCheck.add(chkSiStock);
        panelStockCheck.add(chkNoStock);
        gbcRegistro.gridx = 1;
        gbcRegistro.gridwidth = GridBagConstraints.REMAINDER;
        panelRegistro.add(panelStockCheck, gbcRegistro);
        gbcRegistro.gridwidth = 1;

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 5;
        panelRegistro.add(lblStock, gbcRegistro);
        gbcRegistro.gridx = 1;
        gbcRegistro.gridwidth = GridBagConstraints.REMAINDER;
        panelRegistro.add(spnStock, gbcRegistro);
        gbcRegistro.gridwidth = 1;

        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 6;
        panelRegistro.add(lblFechaRegistro, gbcRegistro);
        gbcRegistro.gridx = 1;
        gbcRegistro.gridwidth = GridBagConstraints.REMAINDER;
        panelRegistro.add(dateChooser, gbcRegistro);  // Usar JDateChooser
        gbcRegistro.gridwidth = 1;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        gbcRegistro.gridx = 0;
        gbcRegistro.gridy = 7;
        gbcRegistro.gridwidth = GridBagConstraints.REMAINDER;
        gbcRegistro.anchor = GridBagConstraints.CENTER;
        gbcRegistro.fill = GridBagConstraints.NONE;
        panelRegistro.add(panelBotones, gbcRegistro);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(panelRegistro, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPaneTabla, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarListeners() {
        btnRegistrar.addActionListener(this);
        btnModificar.addActionListener(this);
        btnEliminar.addActionListener(this);
        chkSiStock.addActionListener(this);
        chkNoStock.addActionListener(this);

        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosProductoSeleccionado();
            }
        });
    }

    public void setController(ProductoController controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller != null) {
            if (e.getSource() == btnRegistrar) {
                controller.registrarProductoEnBaseDeDatos();
            } else if (e.getSource() == btnModificar) {
                controller.actualizarProductoEnBaseDeDatos();
            } else if (e.getSource() == btnEliminar) {
                controller.eliminarProducto();
            } else if (e.getSource() == chkSiStock) {
                spnStock.setEnabled(chkSiStock.isSelected());
                chkNoStock.setSelected(!chkSiStock.isSelected());
            } else if (e.getSource() == chkNoStock) {
                spnStock.setEnabled(!chkNoStock.isSelected());
                chkSiStock.setSelected(!chkNoStock.isSelected());
                if (chkNoStock.isSelected()) {
                    spnStock.setValue(0);
                }
            }
        }
    }

    public void limpiarFormulario() {
        txtNombreProducto.setText("");
        txtMarca.setText("");
        txtPrecio.setText("");
        cmbTipoProducto.setSelectedIndex(0); // Restablecer el combobox
        chkNoStock.setSelected(true);
        spnStock.setValue(0);
        spnStock.setEnabled(false); // Deshabilitar spinner por defecto
        dateChooser.setDate(null);  // Limpiar el JDateChooser
        tablaProductos.clearSelection(); // Limpiar la selección de la tabla
    }


    public void cargarDatosProductoSeleccionado() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombreProducto.setText(tablaProductos.getValueAt(filaSeleccionada, 1).toString());
            cmbTipoProducto.setSelectedItem(tablaProductos.getValueAt(filaSeleccionada, 2).toString());
            txtMarca.setText(tablaProductos.getValueAt(filaSeleccionada, 3).toString());
            txtPrecio.setText(tablaProductos.getValueAt(filaSeleccionada, 4).toString());

            // Checkbox de stock
            boolean enStock = tablaProductos.getValueAt(filaSeleccionada, 5).toString().equals("Sí");
            chkSiStock.setSelected(enStock);
            chkNoStock.setSelected(!enStock);
            spnStock.setEnabled(enStock); // Habilitar/deshabilitar spinner según stock

            spnStock.setValue(Integer.parseInt(tablaProductos.getValueAt(filaSeleccionada, 6).toString()));

            // Cargar la fecha en el JDateChooser
            try {
                // El formato de la tabla es "dd-MM-yyyy", así que parseamos con ese formato
                SimpleDateFormat sdfTabla = new SimpleDateFormat("dd-MM-yyyy");
                String fechaTablaStr = tablaProductos.getValueAt(filaSeleccionada, 7).toString();
                Date date = sdfTabla.parse(fechaTablaStr);
                dateChooser.setDate(date);
            } catch (ParseException | NullPointerException | ClassCastException e) {
                // Manejo de errores: si la fecha no se puede parsear o es nula, limpia el JDateChooser
                System.err.println("Error al cargar la fecha en el JDateChooser: " + e.getMessage());
                dateChooser.setDate(null);
            }
        }
    }

    // Métodos getter para los componentes (necesarios para el controlador)
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnModificar() { return btnModificar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JTextField getTxtNombreProducto() { return txtNombreProducto; }
    public JComboBox<String> getCmbTipoProducto() { return cmbTipoProducto; }
    public JTextField getTxtMarca() { return txtMarca; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JCheckBox getChkSiStock() { return chkSiStock; }
    public JCheckBox getChkNoStock() { return chkNoStock; }
    public JSpinner getSpnStock() { return spnStock; }
    public JTable getTablaProductos() { return tablaProductos; }

    // Metodo corregido: Ahora devuelve el formato "dd-MM-yyyy" que espera el controlador
    public String getTxtFechaRegistro() {
        Date date = dateChooser.getDate();
        if (date != null) {
            // Usa el mismo formato que el controlador espera para la entrada de la vista
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistroProductoView::new);
    }
}
