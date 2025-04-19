package com.example.mallman1.models;

public class Product {
    private String id;
    private String name;
    private double price;
    private int stock;
    private String description;
    private String category;
    private String imageUrl;
    private String retailerId;

    public Product() {
        // Required empty constructor for Firebase
    }

    public Product(String id, String name, double price, int stock, String description, String category, String imageUrl, String retailerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
        this.retailerId = retailerId;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
    public String getRetailerId() { return retailerId; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setRetailerId(String retailerId) { this.retailerId = retailerId; }
} 