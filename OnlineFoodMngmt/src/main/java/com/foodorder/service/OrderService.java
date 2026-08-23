package com.foodorder.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.foodorder.model.Order;

@Service
public class OrderService {

    private List<Order> orders = new ArrayList<>();

    // Add order
    public Order addOrder(Order order) {
        orders.add(order);
        return order;
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orders;
    }

    // Get order by ID
    public Order getOrderById(int id) {

        return orders.stream()
                .filter(order -> order.getOrderId() == id)
                .findFirst()
                .orElse(null);
    }

    // Update complete order
    public Order updateOrder(int id, Order updatedOrder) {

        Order existingOrder = getOrderById(id);

        if (existingOrder != null) {

            existingOrder.setCustomerName(
                    updatedOrder.getCustomerName());

            existingOrder.setFoodItem(
                    updatedOrder.getFoodItem());

            existingOrder.setQuantity(
                    updatedOrder.getQuantity());

            existingOrder.setPrice(
                    updatedOrder.getPrice());

            return existingOrder;
        }

        return null;
    }

    // Delete order
    public String deleteOrder(int id) {

        Order order = getOrderById(id);

        if (order != null) {
            orders.remove(order);
            return "Order deleted successfully";
        }

        return "Order not found";
    }

    // PATCH - Partial update
    public Order partialUpdate(int id, Order updatedOrder) {

        Order existingOrder = getOrderById(id);

        if (existingOrder != null) {

            if (updatedOrder.getCustomerName() != null) {
                existingOrder.setCustomerName(
                        updatedOrder.getCustomerName());
            }

            if (updatedOrder.getFoodItem() != null) {
                existingOrder.setFoodItem(
                        updatedOrder.getFoodItem());
            }

            if (updatedOrder.getQuantity() > 0) {
                existingOrder.setQuantity(
                        updatedOrder.getQuantity());
            }

            if (updatedOrder.getPrice() != null) {
                existingOrder.setPrice(
                        updatedOrder.getPrice());
            }

            return existingOrder;
        }

        return null;
    }

    // CUSTOM METHOD 1
    // Find orders by customer name
    public List<Order> getOrdersByCustomer(String customerName) {

        return orders.stream()
                .filter(order -> order.getCustomerName()
                        .equalsIgnoreCase(customerName))
                .toList();
    }

    // CUSTOM METHOD 2
    // Find orders by food item
    public List<Order> getOrdersByFoodItem(String foodItem) {

        return orders.stream()
                .filter(order -> order.getFoodItem()
                        .equalsIgnoreCase(foodItem))
                .toList();
    }

    // CUSTOM METHOD 3
    // Find orders above particular price
    public List<Order> getOrdersAbovePrice(Double price) {

        return orders.stream()
                .filter(order -> order.getPrice() > price)
                .toList();
    }

    // CUSTOM METHOD 4
    // Find orders above particular quantity
    public List<Order> getOrdersAboveQuantity(int quantity) {

        return orders.stream()
                .filter(order -> order.getQuantity() > quantity)
                .toList();
    }
}