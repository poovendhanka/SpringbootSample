package com.example.demo.controller;

import com.example.demo.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Map<String, Object> createOrder(
            @RequestParam String customer,
            @RequestParam String product,
            @RequestParam int quantity) {

        return orderService.createOrder(customer, product, quantity);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getOrder(@PathVariable int id) {
        return orderService.getOrder(id);
    }

    @PostMapping("/{id}/pay")
    public Map<String, Object> payOrder(@PathVariable int id) {
        return orderService.processPayment(id);
    }

    @PostMapping("/{id}/cancel")
    public Map<String, Object> cancelOrder(@PathVariable int id) {
        return orderService.cancelOrder(id);
    }

    //to check order canceled
     @PostMapping("/{id}/canceled")
    public Map<String, Object> cancelOrdered(@PathVariable int id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/{id}/status")
    public Map<String, Object> getStatus(@PathVariable int id) {
        return orderService.getOrderStatus(id);
    }
}
