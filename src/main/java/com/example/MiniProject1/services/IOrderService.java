package com.example.MiniProject1.services;

import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.payload.Request.OrderRequest;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderRequest orderRequest);

    Order updateOrder(Long id, OrderRequest orderRequest) throws Exception;

    boolean deleteOrder(Long id) throws Exception;

    List<Order> getAllOrders();

    Order getOrderById(Long id) throws Exception;
}
