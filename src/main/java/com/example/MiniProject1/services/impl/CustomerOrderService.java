package com.example.MiniProject1.services.impl;

import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.models.User;
import com.example.MiniProject1.payload.Request.OrderRequest;
import com.example.MiniProject1.repositories.CustomerRepository;
import com.example.MiniProject1.repositories.OrderRepository;
import com.example.MiniProject1.repositories.ProductRepository;
import com.example.MiniProject1.repositories.UserRepository;
import com.example.MiniProject1.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    Định nghĩa các dịch vụ của customer
 */
@Service
public class CustomerOrderService implements IOrderService {
    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    final CustomerRepository customerRepository;

    @Autowired
    private final AuthService authService;

    private final User currentUser;

    public CustomerOrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository, CustomerRepository customerRepository, AuthService authService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.authService = authService;
        this.currentUser = authService.getCurrentUser();
    }
    // lấy ra tất cả đơn hàng của 1 customer cụ thể
    @Override
    public List<Order> getAllOrders() {
        // Lấy thông tin người đăng nhập hiện tại
        User currentUser = authService.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalArgumentException("Access denied");
        }

        // kiểm tra người dùng hiện tại có phải là customer hay không, nếu có trả về tất cả các đơn hàng của họ
        // Lấy danh sách đơn hàng của customer hiện tại
        if (customerRepository.existsById(currentUser.getId())) {
            return orderRepository.findByCustomerId(currentUser.getId());
        }
        return null;
    }

    // lấy id đơn hàng của customer hiện tại
    @Override
    public Order getOrderById(Long id) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new Exception("Order not found");
        }

        Order order = optionalOrder.get();

        // kiểm tra id của người dùng hiện tại có phải là người đặt hàng order này không
        if (order.getCustomer().getUser().getId().equals(currentUser.getId())) {
            return order;
        }
        return null;
    }

    /*
        Kiểm tra có tồn tại sản phẩm không sau đó sẽ cho customer tiến hành đặt hàng
     */
    @Override
    public Order createOrder(OrderRequest orderRequest) {
        if (productRepository.existsById(orderRequest.getProduct().getId())) {
            Order order = new Order();
            order.setOrderDate(orderRequest.getOrderDate());
            order.setProduct(orderRequest.getProduct());
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderRequest orderRequest) throws Exception {
        Order order = getOrderById(id);
        if (order == null) {
            return null;
        }
        order.setOrderDate(orderRequest.getOrderDate());
        order.setProduct(orderRequest.getProduct());
        return orderRepository.save(order);
    }

    @Override
    public boolean deleteOrder(Long id) throws Exception {
        if (orderRepository.existsById(id) && getOrderById(id) != null) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
