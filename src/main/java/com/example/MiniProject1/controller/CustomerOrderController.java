package com.example.MiniProject1.controller;

import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.payload.Request.OrderRequest;
import com.example.MiniProject1.payload.Response.ResponseObject;
import com.example.MiniProject1.services.impl.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/order")
public class CustomerOrderController {
    private final OrderService orderService;

    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{customerId}/view")
    public ResponseEntity<List<Order>> getAllOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        List<Order> orders = orderService.getAllOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{customerId}/get/{orderId}")
    public ResponseEntity<ResponseObject> getOrderByIdByCustomer(@PathVariable("customerId") Long customerId, @PathVariable("orderId") Long orderId) {
        Order order = orderService.getOrderByCustomerId(customerId, orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "get order successfully", order));
    }

    @PostMapping("/{customerId}/create")
    public ResponseEntity<ResponseObject> createOrderByCustomer(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrderByCustomer(orderRequest);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("fail", "create order failed", ""));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject("ok", "create successfully", order));
    }

    @PutMapping("/{customerId}/update/{orderId}")
    public ResponseEntity<?> updateOrderByCustomer(@PathVariable("customerId") Long customerId, @PathVariable("orderId") Long orderId, @RequestBody OrderRequest orderRequest) throws Exception {
        Order order = orderService.updateOrderByCustomer(customerId, orderId, orderRequest);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "update successfully", order));

    }

    @DeleteMapping("/{customerId}/delete/{orderId}")
    public ResponseEntity<ResponseObject> deleteOrderByCustomer(@PathVariable("customerId") Long customerId, @PathVariable("orderId") Long orderId) throws Exception {
        boolean deleted = orderService.deleteOrderByCustomer(customerId, orderId);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "delete successfully", ""));

    }
}
