package com.example.MiniProject1.payload.Request;

public class ProductRequest {
    private String name;
    private String category;
    private Double price;

    // Constructors, getters, and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

