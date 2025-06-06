package pe.edu.vallegrande.model;

import java.util.Date;

public class Product { // Renamed
    private int id;
    private String name; // Renamed from nombre
    private String type; // Renamed from tipo
    private String brand; // Renamed from marca
    private double price;
    private boolean hasStock; // Renamed from hayStock
    private int stock;
    private Date registrationDate; // Renamed from fechaRegistro

    public Product() { // Renamed
    }

    public Product(int id, String name, String type, String brand, double price, boolean hasStock, int stock) { // Renamed
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.hasStock = hasStock;
        this.stock = stock;
    }

    public Product(int id, String name, String type, String brand, double price, boolean hasStock, int stock, Date registrationDate) { // Renamed
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.hasStock = hasStock;
        this.stock = stock;
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { // Renamed
        return name;
    }

    public void setName(String name) { // Renamed
        this.name = name;
    }

    public String getType() { // Renamed
        return type;
    }

    public void setType(String type) { // Renamed
        this.type = type;
    }

    public String getBrand() { // Renamed
        return brand;
    }

    public void setBrand(String brand) { // Renamed
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHasStock() { // Renamed
        return hasStock;
    }

    public void setHasStock(boolean hasStock) { // Renamed
        this.hasStock = hasStock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getRegistrationDate() { // Renamed
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) { // Renamed
        this.registrationDate = registrationDate;
    }
}