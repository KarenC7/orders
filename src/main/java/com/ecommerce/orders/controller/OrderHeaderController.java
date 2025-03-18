package com.ecommerce.orders.controller;
import com.ecommerce.orders.dto.OrderDto;
import com.ecommerce.orders.dto.OrderHeaderDto;
import com.ecommerce.orders.service.OrderHeaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderHeaders")
@RequiredArgsConstructor
@Slf4j
public class OrderHeaderController {

    private final OrderHeaderService orderHeaderService;

    @Operation(summary = "Create an order header with order items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid token")
    })
    @PostMapping
    public ResponseEntity<OrderHeaderDto> createOrderHeader(@RequestBody OrderHeaderDto dto) {
        log.info("Received request to create order header with items");
        OrderHeaderDto createdHeader = orderHeaderService.createOrderHeader(dto);
        return ResponseEntity.ok(createdHeader);
    }

    @Operation(summary = "Get order header by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get order header successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid token")
    })
    @GetMapping("/{orderHeaderId}")
    public ResponseEntity<OrderHeaderDto> getOrderHeaderById(@PathVariable Long orderHeaderId) {
        log.info("Received request to get order header with id {}", orderHeaderId);
        OrderHeaderDto headerDto = orderHeaderService.getOrderHeaderById(orderHeaderId);
        return ResponseEntity.ok(headerDto);
    }


    @Operation(summary = "Update status in a order header by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid token")
    })
    @PatchMapping("/{orderHeaderId}/status")
    public ResponseEntity<OrderHeaderDto> updateOrderItemStatus(@PathVariable Long orderHeaderId, @RequestBody String status) {
        log.info("Received request to update order item {} status to {}", orderHeaderId, status);
        OrderHeaderDto updatedOrder = orderHeaderService.updateOrderHeaderStatus(orderHeaderId, status);
        return ResponseEntity.ok(updatedOrder);
    }



    @Operation(summary = "Get order Items by customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid token")
    })
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderHeaderDto>> getOrderItemsByCustomerId(@PathVariable Long customerId) {
        log.info("Received request to get order items for customer id {}", customerId);
        List<OrderHeaderDto> orderItems = orderHeaderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orderItems);
    }
}