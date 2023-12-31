package com.example.MiniProject1.services.impl;

import com.example.MiniProject1.models.Customer;
import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.payload.Request.OrderRequest;
import com.example.MiniProject1.repositories.CustomerRepository;
import com.example.MiniProject1.repositories.OrderRepository;
import com.example.MiniProject1.repositories.ProductRepository;
import com.example.MiniProject1.security.jwt.CustomUserDetails;
import com.example.MiniProject1.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    /*
        Dịch vụ cho Admin với use case order
     */
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
        if (productRepository.existsById(orderRequest.getProductId())
                && customerRepository.existsById(orderRequest.getCustomerId())) {
            Order order = new Order();
            order.setOrderDate(orderRequest.getOrderDate());
            order.setProduct(productRepository.findById(orderRequest.getProductId()).orElse(null));
            order.setCustomer(customerRepository.findById(orderRequest.getCustomerId()).orElse(null));
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderRequest orderRequest) throws Exception {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        order.setOrderDate(orderRequest.getOrderDate());
        if (productRepository.findById(orderRequest.getProductId()) == null) {
            throw new Exception("ProductId not exists");
        }
        order.setProduct(productRepository.findById(orderRequest.getProductId()).orElse(null));
        if (customerRepository.findById(orderRequest.getCustomerId()) == null) {
            throw new Exception("CustomerId note exists");
        }
        order.setCustomer(customerRepository.findById(orderRequest.getCustomerId()).orElse(null));
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

    /*
        Dịch vụ cho customer với use case order
     */
    public List<Order> getAllOrdersByCustomerId(Authentication authentication) throws Exception {
        return orderRepository.findByCustomerId(getCustomerIdFromContext(authentication));
    }

    public Order getOrderByCustomerId(Authentication authentication, Long orderId) throws Exception {
        return orderRepository.findByCustomerIdAndId(getCustomerIdFromContext(authentication), orderId);
    }

    public Order createOrderByCustomer(OrderRequest orderRequest) {
        if (productRepository.existsById(orderRequest.getProductId())) {
            Order order = new Order();
            order.setOrderDate(orderRequest.getOrderDate());
            order.setProduct(productRepository.findById(orderRequest.getProductId()).orElse(null));
            order.setCustomer(customerRepository.findById(orderRequest.getCustomerId()).orElse(null));
            return orderRepository.save(order);
        }
        return null;
    }


    public Order updateOrderByCustomer(Authentication authentication, Long orderId, OrderRequest orderRequest) throws Exception {
        Order order = orderRepository.findById(orderId).orElse(null);
        // kiểm tra người dùng có được phép thao tác trên orderId này không
        if (!orderRepository.existsByCustomerId(getCustomerIdFromContext(authentication))) {
            throw new Exception("No order founded with customerId");
        }
        order.setOrderDate(orderRequest.getOrderDate());
        if (!productRepository.findById(orderRequest.getProductId()).isPresent()) {
            throw new Exception("ProductId not exists");
        }
        order.setProduct(productRepository.findById(orderRequest.getProductId()).orElse(null));
        if (customerRepository.findById(orderRequest.getCustomerId()) == null) {
            throw new Exception("CustomerId not exists");
        }
        return orderRepository.save(order);
    }

    public boolean deleteOrderByCustomer(Authentication authentication, Long orderId) throws Exception {
        if (!orderRepository.existsByCustomerId(getCustomerIdFromContext(authentication))) {
            throw new Exception("No order founded with customerId");
        }
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

    public Long getCustomerIdFromContext(Authentication authentication) throws Exception {
        if (authentication == null) {
            throw new Exception("lỗi rồi fix đi");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Customer customer = customerRepository.findByUserId(userDetails.getId()).orElse(null);
        return customer.getId();
    }
}
