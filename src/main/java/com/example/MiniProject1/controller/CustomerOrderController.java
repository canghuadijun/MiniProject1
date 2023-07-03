package com.example.MiniProject1.controller;

import com.example.MiniProject1.models.Order;
import com.example.MiniProject1.payload.Request.OrderRequest;
import com.example.MiniProject1.payload.Response.ResponseObject;
import com.example.MiniProject1.services.impl.CustomerOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/order")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerOrderController {
    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/view")
    public ResponseEntity <List<Order>> getAllOrders() {
        List<Order> orders = customerOrderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable("id") Long id) throws Exception {
        Order order = customerOrderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "get order successfully", order));

    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = customerOrderService.createOrder(orderRequest);
        if(order == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("fail", "create order failed", ""));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject("ok", "create successfully", order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @RequestBody OrderRequest orderRequest) throws Exception {
        Order order = customerOrderService.updateOrder(id, orderRequest);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));

        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "update successfully", order));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteOrder(@PathVariable("id") Long id) throws Exception {
        boolean deleted = customerOrderService.deleteOrder(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("fail", "not found", ""));


        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "delete successfully", ""));

    }
}
