package com.example.MiniProject1.controller;

import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.payload.Request.OrderRequest;
import com.example.MiniProject1.payload.Response.ResponseObject;
import com.example.MiniProject1.repositories.CustomerRepository;
import com.example.MiniProject1.services.impl.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerOrderController {
    private final OrderService orderService;
    private final CustomerRepository customerRepository;

    public CustomerOrderController(OrderService orderService, CustomerRepository customerRepository) {
        this.orderService = orderService;
        this.customerRepository = customerRepository;
    }


    @GetMapping("/customer/{customerId}/order/view")
    public ResponseEntity<List<Order>> getAllOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        List<Order> orders = orderService.getAllOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/customer/order/get/{orderId}")
    public ResponseEntity<ResponseObject> getOrderByIdByCustomer( @PathVariable("orderId") Long orderId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Lấy customerId hiện tại từ SecurityContextHolder
        Long customerId = orderService.getCustomerIdFromContext(authentication);
        Order order = orderService.getOrderByCustomerId(customerId, orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "get order successfully", order));
    }

    @PostMapping("/customer/order/create")
    public ResponseEntity<ResponseObject> createOrderByCustomer(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrderByCustomer(orderRequest);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("fail", "create order failed", ""));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject("ok", "create successfully", order));
    }

    @PutMapping("/customer/order/update/{orderId}")
    public ResponseEntity<?> updateOrderByCustomer(@PathVariable("orderId") Long orderId, @RequestBody OrderRequest orderRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Lấy thông tin người dùng hiện tại từ SecurityContextHolder
        Long customerId = orderService.getCustomerIdFromContext(authentication);
        Order order = orderService.updateOrderByCustomer(customerId, orderId, orderRequest);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "update successfully", order));
    }

    @DeleteMapping("/customer/order/delete/{orderId}")
    public ResponseEntity<ResponseObject> deleteOrderByCustomer(@PathVariable("orderId") Long orderId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Lấy thông tin người dùng hiện tại từ SecurityContextHolder
        Long customerId = orderService.getCustomerIdFromContext(authentication);
        boolean deleted = orderService.deleteOrderByCustomer(customerId, orderId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "delete successfully", ""));

    }
}
