package com.ecommerce.orders.controller;
import com.ecommerce.orders.dto.OrderHeaderDto;
import com.ecommerce.orders.service.OrderHeaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderHeaders")
@RequiredArgsConstructor
@Slf4j
public class OrderHeaderController {

    private final OrderHeaderService orderHeaderService;

    // Create an order header with order items (payload does not include any IDs or prices)
    @PostMapping
    public ResponseEntity<OrderHeaderDto> createOrderHeader(@RequestBody OrderHeaderDto dto) {
        log.info("Received request to create order header with items");
        OrderHeaderDto createdHeader = orderHeaderService.createOrderHeader(dto);
        return ResponseEntity.ok(createdHeader);
    }

    // Get order header by ID
    @GetMapping("/{orderHeaderId}")
    public ResponseEntity<OrderHeaderDto> getOrderHeaderById(@PathVariable Long orderHeaderId) {
        log.info("Received request to get order header with id {}", orderHeaderId);
        OrderHeaderDto headerDto = orderHeaderService.getOrderHeaderById(orderHeaderId);
        return ResponseEntity.ok(headerDto);
    }
}