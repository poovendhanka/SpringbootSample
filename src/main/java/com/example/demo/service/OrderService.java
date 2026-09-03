package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class OrderService {

    private final Map<Integer, Map<String, Object>> orders = new HashMap<>();
    private int sequence = 1;

    public Map<String, Object> createOrder(
            String customer,
            String product,
            int quantity) {

        if (customer == null || customer.isBlank()) {
            return response("FAILED", "Customer name is required");
        }

        if (product == null || product.isBlank()) {
            return response("FAILED", "Product name is required");
        }

        if (quantity <= 0) {
            return response("FAILED", "Quantity must be greater than zero");
        }

        int orderId = sequence++;

        double unitPrice = getProductPrice(product);

        if (unitPrice == 0) {
            return response("FAILED", "Unknown product");
        }

        double totalAmount = unitPrice * quantity;

        if (quantity >= 5) {
            totalAmount = totalAmount * 0.90;
        }

        Map<String, Object> order = new LinkedHashMap<>();
        order.put("id", orderId);
        order.put("customer", customer);
        order.put("product", product);
        order.put("quantity", quantity);
        order.put("amount", totalAmount);
        order.put("paymentStatus", "PENDING");
        order.put("orderStatus", "CREATED");

        orders.put(orderId, order);

        return order;
    }

    public Map<String, Object> getOrder(int id) {

        Map<String, Object> order = orders.get(id);

        if (order == null) {
            return response("NOT_FOUND", "Order does not exist");
        }

        return order;
    }

    public Map<String, Object> processPayment(int id) {

        Map<String, Object> order = orders.get(id);

        if (order == null) {
            return response("NOT_FOUND", "Order does not exist");
        }

        if ("CANCELLED".equals(order.get("orderStatus"))) {
            return response("FAILED", "Cancelled order cannot be paid");
        }

        if ("PAID".equals(order.get("paymentStatus"))) {
            return response("FAILED", "Order is already paid");
        }

        double amount = (double) order.get("amount");

        if (amount > 10000) {
            order.put("paymentStatus", "REVIEW_REQUIRED");
            order.put("orderStatus", "PAYMENT_REVIEW");

            return response(
                    "REVIEW_REQUIRED",
                    "High value payment requires manual review"
            );
        }

        order.put("paymentStatus", "PAID");
        order.put("orderStatus", "CONFIRMED");

        return order;
    }

    public Map<String, Object> cancelOrder(int id) {

        Map<String, Object> order = orders.get(id);

        if (order == null) {
            return response("NOT_FOUND", "Order does not exist");
        }

        if ("CANCELLED".equals(order.get("orderStatus"))) {
            return response("FAILED", "Order is already cancelled");
        }

        if ("PAID".equals(order.get("paymentStatus"))) {

            double amount = (double) order.get("amount");

            if (amount > 5000) {
                order.put("orderStatus", "REFUND_PENDING");
                return response(
                        "REFUND_PENDING",
                        "Paid high-value order requires refund approval"
                );
            }

            order.put("paymentStatus", "REFUNDED");
        }

        order.put("orderStatus", "CANCELLED");

        return order;
    }

    public Map<String, Object> getOrderStatus(int id) {

        Map<String, Object> order = orders.get(id);

        if (order == null) {
            return response("NOT_FOUND", "Order does not exist");
        }

        Map<String, Object> status = new LinkedHashMap<>();
        status.put("orderId", id);
        status.put("orderStatus", order.get("orderStatus"));
        status.put("paymentStatus", order.get("paymentStatus"));

        return status;
    }

    private double getProductPrice(String product) {

        return switch (product.toLowerCase()) {
            case "laptop" -> 50000;
            case "phone" -> 20000;
            case "keyboard" -> 2000;
            case "mouse" -> 1000;
            default -> 0;
        };
    }

    private Map<String, Object> response(String status, String message) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);

        return response;
    }
}