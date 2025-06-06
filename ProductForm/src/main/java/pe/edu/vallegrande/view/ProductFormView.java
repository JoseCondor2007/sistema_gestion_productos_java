package pe.edu.vallegrande.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import pe.edu.vallegrande.controller.ProductController;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class ProductFormView extends JFrame implements ActionListener { // Renamed

    // Componentes de la interfaz
    private JLabel lblProductName, lblProductType, lblBrand, lblPrice,
            lblHasStock, lblStock, lblRegistrationDate;
    private JTextField txtProductName, txtBrand, txtPrice;
    private JComboBox<String> cmbProductType;
    private JCheckBox chkYesStock, chkNoStock;
    private JSpinner spnStock;
    private JButton btnRegister, btnModify, btnDelete;
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPaneTable;

    // Componente para la fecha (JDateChooser)
    private JDateChooser dateChooser;

    private ProductController controller;

    public ProductFormView() { // Renamed
        initializeComponents();
        configureWindow();
        arrangeComponents();
        addListeners();
    }

    private void initializeComponents() {
        lblProductName = new JLabel("Nombre del producto:");
        lblProductType = new JLabel("Tipo de producto:");
        lblBrand = new JLabel("Marca:");
        lblPrice = new JLabel("Precio:");
        lblHasStock = new JLabel("¿Hay en stock?");
        lblStock = new JLabel("Stock:");
        lblRegistrationDate = new JLabel("Fecha de Registro:");

        txtProductName = new JTextField(30);
        txtBrand = new JTextField(30);
        txtPrice = new JTextField(15);

        cmbProductType = new JComboBox<>(new String[]{"Para motos", "Para carros", "Para bicicletas", "Para camiones"});
        cmbProductType.setPreferredSize(new Dimension(200, cmbProductType.getPreferredSize().height));

        chkYesStock = new JCheckBox("Sí");
        chkNoStock = new JCheckBox("No");
        ButtonGroup stockGroup = new ButtonGroup();
        stockGroup.add(chkYesStock);
        stockGroup.add(chkNoStock);
        chkNoStock.setSelected(true);

        spnStock = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        spnStock.setEnabled(false);
        spnStock.setPreferredSize(new Dimension(100, spnStock.getPreferredSize().height));

        btnRegister = new JButton("Registrar");
        btnModify = new JButton("Modificar");
        btnDelete = new JButton("Eliminar");

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Tipo", "Marca", "Precio", "¿En Stock?", "Stock", "Fecha Registro"}, 0);
        productsTable = new JTable(tableModel);
        scrollPaneTable = new JScrollPane(productsTable);
        scrollPaneTable.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));

        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(120, dateChooser.getPreferredSize().height));

        try {
            ImageIcon originalIconRegistrar = new ImageIcon(getClass().getResource("/img/registrar.png"));
            Image imageRegistrar = originalIconRegistrar.getImage();
            Image scaledImageRegistrar = imageRegistrar.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon iconRegistrar = new ImageIcon(scaledImageRegistrar);
            btnRegister.setIcon(iconRegistrar);

            ImageIcon originalIconModificar = new ImageIcon(getClass().getResource("/img/modificar.png"));
            Image imageModificar = originalIconModificar.getImage();
            Image scaledImageModificar = imageModificar.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon iconModificar = new ImageIcon(scaledImageModificar);
            btnModify.setIcon(iconModificar);

            ImageIcon originalIconEliminar = new ImageIcon(getClass().getResource("/img/eliminar.png"));
            Image imageEliminar = originalIconEliminar.getImage();
            Image scaledImageEliminar = imageEliminar.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon iconEliminar = new ImageIcon(scaledImageEliminar);
            btnDelete.setIcon(iconEliminar);

        } catch (Exception e) {
            System.err.println("Error al cargar o escalar las imágenes de los botones: " + e.getMessage());
            e.printStackTrace();
        }

        btnRegister.setBackground(new Color(144, 238, 144));
        btnModify.setBackground(new Color(255, 255, 153));
        btnDelete.setBackground(new Color(255, 153, 153));
    }

    private void configureWindow() {
        setTitle("Formulario de Registro de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        java.net.URL imgURL = getClass().getResource("/img/rueda.png");
        if (imgURL == null) {
            System.err.println("¡No se pudo encontrar la imagen: /img/rueda.png!");
        } else {
            ImageIcon icon = new ImageIcon(imgURL);
            setIconImage(icon.getImage());
        }
    }

    private void arrangeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel registrationPanel = new JPanel(new GridBagLayout());
        registrationPanel.setBorder(BorderFactory.createTitledBorder("Registro de Producto"));
        GridBagConstraints gbcRegistration = new GridBagConstraints();
        gbcRegistration.insets = new Insets(5, 5, 5, 5);
        gbcRegistration.anchor = GridBagConstraints.WEST;
        gbcRegistration.fill = GridBagConstraints.HORIZONTAL;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 0;
        registrationPanel.add(lblProductName, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(txtProductName, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 1;
        registrationPanel.add(lblProductType, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.weightx = 1.0;
        registrationPanel.add(cmbProductType, gbcRegistration);
        gbcRegistration.weightx = 0.0;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 2;
        registrationPanel.add(lblBrand, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(txtBrand, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 3;
        registrationPanel.add(lblPrice, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(txtPrice, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 4;
        registrationPanel.add(lblHasStock, gbcRegistration);
        JPanel stockCheckPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stockCheckPanel.add(chkYesStock);
        stockCheckPanel.add(chkNoStock);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(stockCheckPanel, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 5;
        registrationPanel.add(lblStock, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(spnStock, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 6;
        registrationPanel.add(lblRegistrationDate, gbcRegistration);
        gbcRegistration.gridx = 1;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        registrationPanel.add(dateChooser, gbcRegistration);
        gbcRegistration.gridwidth = 1;

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(btnRegister);
        buttonsPanel.add(btnModify);
        buttonsPanel.add(btnDelete);
        gbcRegistration.gridx = 0;
        gbcRegistration.gridy = 7;
        gbcRegistration.gridwidth = GridBagConstraints.REMAINDER;
        gbcRegistration.anchor = GridBagConstraints.CENTER;
        gbcRegistration.fill = GridBagConstraints.NONE;
        registrationPanel.add(buttonsPanel, gbcRegistration);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(registrationPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPaneTable, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addListeners() {
        btnRegister.addActionListener(this);
        btnModify.addActionListener(this);
        btnDelete.addActionListener(this);
        chkYesStock.addActionListener(this);
        chkNoStock.addActionListener(this);

        productsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedProductData();
            }
        });
    }

    public void setController(ProductController controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (controller != null) {
            if (e.getSource() == btnRegister) {
                controller.registerProductInDatabase();
            } else if (e.getSource() == btnModify) {
                controller.updateProductInDatabase();
            } else if (e.getSource() == btnDelete) {
                controller.deleteProduct();
            } else if (e.getSource() == chkYesStock) {
                spnStock.setEnabled(chkYesStock.isSelected());
                chkNoStock.setSelected(!chkYesStock.isSelected());
            } else if (e.getSource() == chkNoStock) {
                spnStock.setEnabled(!chkNoStock.isSelected());
                chkYesStock.setSelected(!chkNoStock.isSelected());
                if (chkNoStock.isSelected()) {
                    spnStock.setValue(0);
                }
            }
        }
    }

    public void clearForm() {
        txtProductName.setText("");
        txtBrand.setText("");
        txtPrice.setText("");
        cmbProductType.setSelectedIndex(0);
        chkNoStock.setSelected(true);
        spnStock.setValue(0);
        spnStock.setEnabled(false);
        dateChooser.setDate(null);
        productsTable.clearSelection();
    }

    public void loadSelectedProductData() {
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow != -1) {
            txtProductName.setText(productsTable.getValueAt(selectedRow, 1).toString());
            cmbProductType.setSelectedItem(productsTable.getValueAt(selectedRow, 2).toString());
            txtBrand.setText(productsTable.getValueAt(selectedRow, 3).toString());
            txtPrice.setText(productsTable.getValueAt(selectedRow, 4).toString());

            boolean inStock = productsTable.getValueAt(selectedRow, 5).toString().equals("Sí");
            chkYesStock.setSelected(inStock);
            chkNoStock.setSelected(!inStock);
            spnStock.setEnabled(inStock);

            spnStock.setValue(Integer.parseInt(productsTable.getValueAt(selectedRow, 6).toString()));

            try {
                SimpleDateFormat sdfTable = new SimpleDateFormat("dd-MM-yyyy");
                String dateTableStr = productsTable.getValueAt(selectedRow, 7).toString();
                Date date = sdfTable.parse(dateTableStr);
                dateChooser.setDate(date);
            } catch (ParseException | NullPointerException | ClassCastException e) {
                System.err.println("Error al cargar la fecha en el JDateChooser: " + e.getMessage());
                dateChooser.setDate(null);
            }
        }
    }

    // Métodos getter para los componentes (necesarios para el controlador)
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRegistrar() { return btnRegister; }
    public JButton getBtnModificar() { return btnModify; }
    public JButton getBtnEliminar() { return btnDelete; }
    public JTextField getTxtNombreProducto() { return txtProductName; }
    public JComboBox<String> getCmbTipoProducto() { return cmbProductType; }
    public JTextField getTxtMarca() { return txtBrand; }
    public JTextField getTxtPrecio() { return txtPrice; }
    public JCheckBox getChkSiStock() { return chkYesStock; }
    public JCheckBox getChkNoStock() { return chkNoStock; }
    public JSpinner getSpnStock() { return spnStock; }
    public JTable getTablaProductos() { return productsTable; }

    public String getTxtFechaRegistro() {
        Date date = dateChooser.getDate();
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(date);
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductFormView::new); // Renamed
    }
}