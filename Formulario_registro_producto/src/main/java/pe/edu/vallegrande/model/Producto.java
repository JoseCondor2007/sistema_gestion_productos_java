package pe.edu.vallegrande.model;

import java.util.Date;

public class Producto {
    private int id;
    private String nombre;
    private String tipo;
    private String marca;
    private double precio;
    private boolean hayStock;
    private int stock;
    private Date fechaRegistro;

    public Producto() {
    }

    public Producto(int id, String nombre, String tipo, String marca, double precio, boolean hayStock, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.precio = precio;
        this.hayStock = hayStock;
        this.stock = stock;
    }

    public Producto(int id, String nombre, String tipo, String marca, double precio, boolean hayStock, int stock, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.precio = precio;
        this.hayStock = hayStock;
        this.stock = stock;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isHayStock() {
        return hayStock;
    }

    public void setHayStock(boolean hayStock) {
        this.hayStock = hayStock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}