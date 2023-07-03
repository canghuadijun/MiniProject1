package com.example.MiniProject1.payload.Request;

import com.example.MiniProject1.models.Customer;
import com.example.MiniProject1.models.Product;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRequest {
    private LocalDateTime orderDate;
    private Long productId;
    private Long customerId;

    // Constructors, getters, and setters

    public OrderRequest(LocalDateTime orderDate, Long productId, Long customerId) {
        this.orderDate = orderDate;
        this.productId = productId;
        this.customerId = customerId;
    }

    public OrderRequest() {
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}

