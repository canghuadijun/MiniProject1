package com.example.MiniProject1.payload.Request;

import com.example.MiniProject1.models.Customer;
import com.example.MiniProject1.models.Product;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {
    private LocalDateTime orderDate;
    private Product product;
    private Customer customer;

    // Constructors, getters, and setters

    public OrderRequest(LocalDateTime orderDate, Product product, Customer customer) {
        this.orderDate = orderDate;
        this.product = product;
        this.customer = customer;
    }

    public OrderRequest() {
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

