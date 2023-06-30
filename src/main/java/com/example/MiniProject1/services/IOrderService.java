package com.example.MiniProject1.services;

import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.payload.Request.OrderRequest;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderRequest orderRequest);

    Order updateOrder(Long id, OrderRequest orderRequest);

    boolean deleteOrder(Long id);

    List<Order> getAllOrders();

    Order getOrderById(Long id);
}
