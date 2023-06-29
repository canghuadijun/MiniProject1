package com.example.MiniProject1.services;

import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.payload.Request.OrderRequest;
import com.example.MiniProject1.repositories.OrderRepository;
import com.example.MiniProject1.repositories.ProductRepository;
import com.example.MiniProject1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order createOrder(OrderRequest orderRequest) {
        if (productRepository.existsById(orderRequest.getProduct().getId())
                && userRepository.existsById(orderRequest.getCustomer().getId())) {
            Order order = new Order();
            order.setOrderDate(orderRequest.getOrderDate());
            order.setProduct(orderRequest.getProduct());
            order.setCustomer(orderRequest.getCustomer());
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderRequest orderRequest) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        order.setOrderDate(orderRequest.getOrderDate());
        order.setProduct(orderRequest.getProduct());
        order.setCustomer(orderRequest.getCustomer());
        return orderRepository.save(order);
    }

    @Override
    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

