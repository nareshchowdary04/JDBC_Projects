package com.foodorder.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodorder.model.Order;
import com.foodorder.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST - Add order
    @PostMapping
    public ResponseEntity<Order> addOrder(
            @Valid @RequestBody Order order) {

        return ResponseEntity.ok(
                orderService.addOrder(order));
    }

    // GET - Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders());
    }

    // GET - Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(
            @PathVariable int id) {

        Order order = orderService.getOrderById(id);

        if (order != null) {
            return ResponseEntity.ok(order);
        }

        return ResponseEntity
                .status(404)
                .body("Order not found");
    }

    // PUT - Update complete order
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @PathVariable int id,
            @Valid @RequestBody Order order) {

        Order updatedOrder =
                orderService.updateOrder(id, order);

        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        }

        return ResponseEntity
                .status(404)
                .body("Order not found");
    }

    // DELETE - Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable int id) {

        String result = orderService.deleteOrder(id);

        if (result.equals("Order deleted successfully")) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity
                .status(404)
                .body(result);
    }

    // PATCH - Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(
            @PathVariable int id,
            @RequestBody Order order) {

        Order updatedOrder =
                orderService.partialUpdate(id, order);

        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        }

        return ResponseEntity
                .status(404)
                .body("Order not found");
    }

    // CUSTOM API 1
    // Find orders by customer
    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(
            @PathVariable String customerName) {

        return ResponseEntity.ok(
                orderService.getOrdersByCustomer(customerName));
    }

    // CUSTOM API 2
    // Find orders by food item
    @GetMapping("/food/{foodItem}")
    public ResponseEntity<List<Order>> getOrdersByFoodItem(
            @PathVariable String foodItem) {

        return ResponseEntity.ok(
                orderService.getOrdersByFoodItem(foodItem));
    }

    // CUSTOM API 3
    // Find orders above price
    @GetMapping("/price-above/{price}")
    public ResponseEntity<List<Order>> getOrdersAbovePrice(
            @PathVariable Double price) {

        return ResponseEntity.ok(
                orderService.getOrdersAbovePrice(price));
    }

    // CUSTOM API 4
    // Find orders above quantity
    @GetMapping("/quantity-above/{quantity}")
    public ResponseEntity<List<Order>> getOrdersAboveQuantity(
            @PathVariable int quantity) {

        return ResponseEntity.ok(
                orderService.getOrdersAboveQuantity(quantity));
    }
}