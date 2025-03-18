package com.ecommerce.orders.controller;
import com.ecommerce.orders.dto.OrderDto;
import com.ecommerce.orders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create an order item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid token")
    })
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        log.info("Received request to create order item");
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.ok(createdOrder);
    }

    @Operation(summary = "Get an order item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid token")
    })
    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderDto> getOrderItemById(@PathVariable Long orderId) {
        log.info("Received request to get order item with id {}", orderId);
        OrderDto orderDto = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderDto);
    }


}