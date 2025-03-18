package com.ecommerce.orders.service.impl;

import com.ecommerce.orders.adapter.FakeStoreApiClient;
import com.ecommerce.orders.model.Order;
import com.ecommerce.orders.dto.OrderDto;
import com.ecommerce.orders.exception.InvalidOrderException;
import com.ecommerce.orders.exception.OrderNotFoundException;
import com.ecommerce.orders.repository.OrderRepository;
import com.ecommerce.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final FakeStoreApiClient fakeStoreApiClient; // Se utiliza para validar que el productId exista

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("Creating order for customer {} with product {}", orderDto.getCustomerId(), orderDto.getProductId());

        // Validate if the product is available in FakeStoreApiClient
        if (fakeStoreApiClient.getProductById(orderDto.getProductId()) == null) {
            log.error("Invalid product id: {}", orderDto.getProductId());
            throw new InvalidOrderException("Product not found with id: " + orderDto.getProductId());
        }

        Order order = Order.builder()
                .customerId(orderDto.getCustomerId())
                .productId(orderDto.getProductId())
                .quantity(orderDto.getQuantity())
                .price(orderDto.getPrice())
                .status(orderDto.getStatus() != null ? orderDto.getStatus() : "PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        order = orderRepository.save(order);
        log.info("Order created with id {}", order.getId());
        return mapToDto(order);
    }

    @Override
    @Cacheable(value = "orders", key = "#orderId")
    public OrderDto getOrderById(Long orderId) {
        log.info("Retrieving order with id {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        return mapToDto(order);
    }

    @Override
    @CacheEvict(value = "orders", key = "#orderId")
    public OrderDto updateOrderStatus(Long orderId, String status) {
        log.info("Updating order id {} status to {}", orderId, status);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        order.setStatus(status);
        order = orderRepository.save(order);
        return mapToDto(order);
    }

    @Override
    @Cacheable(value = "ordersByCustomer", key = "#customerId")
    public List<OrderDto> getOrdersByCustomerId(Long customerId) {
        log.info("Retrieving orders for customer id {}", customerId);
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private OrderDto mapToDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getCustomerId(),
                order.getProductId(),
                order.getQuantity(),
                order.getPrice(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}