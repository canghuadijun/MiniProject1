package com.example.MiniProject1.services;

import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.payload.Request.OrderRequest;

import java.util.List;

public interface IOrderService {
    public abstract Order createOrder(OrderRequest orderRequest);

    public abstract Order updateOrder(Long id, OrderRequest orderRequest);

    public abstract boolean deleteOrder(Long id);

    public abstract List<Order> getAllOrders();

    public abstract Order getOrderById(Long id);
}
