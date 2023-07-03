package com.example.MiniProject1.controller;

import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.payload.Request.OrderRequest;
import com.example.MiniProject1.payload.Response.ResponseObject;
import com.example.MiniProject1.services.impl.AdminOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {
    private final AdminOrderService orderService;

    public AdminOrderController(AdminOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/view")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "get order successfully", order));

    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("fail", "create order failed", ""));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject("ok", "create successfully", order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @RequestBody OrderRequest orderRequest) {
        Order order = orderService.updateOrder(id, orderRequest);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "update successfully", order));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteOrder(@PathVariable("id") Long id) {
        boolean deleted = orderService.deleteOrder(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "delete successfully", ""));

    }
}

