package com.ecommerce.orders.controller;
import com.ecommerce.orders.dto.OrderDto;
import com.ecommerce.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    // Create an order item
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        log.info("Received request to create order item");
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.ok(createdOrder);
    }

    // Get an order item by ID
    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderDto> getOrderItemById(@PathVariable Long orderId) {
        log.info("Received request to get order item with id {}", orderId);
        OrderDto orderDto = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderDto);
    }

    // Update order item status
    @PatchMapping("/{orderItemId}/status")
    public ResponseEntity<OrderDto> updateOrderItemStatus(@PathVariable Long orderItemId, @RequestBody String status) {
        log.info("Received request to update order item {} status to {}", orderItemId, status);
        OrderDto updatedOrder = orderService.updateOrderStatus(orderItemId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    // Get order items by customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDto>> getOrderItemsByCustomerId(@PathVariable Long customerId) {
        log.info("Received request to get order items for customer id {}", customerId);
        List<OrderDto> orderItems = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orderItems);
    }
}