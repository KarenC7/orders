package com.ecommerce.orders.service.impl;

import com.ecommerce.orders.adapter.FakeStoreApiClient;
import com.ecommerce.orders.model.Order;
import com.ecommerce.orders.dto.OrderDto;
import com.ecommerce.orders.exception.InvalidOrderException;
import com.ecommerce.orders.exception.OrderNotFoundException;
import com.ecommerce.orders.model.OrderHeader;
import com.ecommerce.orders.repository.OrderHeaderRepository;
import com.ecommerce.orders.repository.OrderRepository;
import com.ecommerce.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderHeaderRepository orderHeaderRepository;
    private final FakeStoreApiClient fakeStoreApiClient;

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("Creating order item for customer {} with product {}", orderDto.getCustomerId(), orderDto.getProductId());

        // Validate that the product exists and get its price
        var productDto = fakeStoreApiClient.getProductById(orderDto.getProductId());
        if (productDto == null) {
            log.error("Product not found with id {}", orderDto.getProductId());
            throw new InvalidOrderException("Product not found with id: " + orderDto.getProductId());
        }

        BigDecimal pricePerProduct = BigDecimal.valueOf(productDto.getPrice());
        Integer quantity = orderDto.getQuantity();
        BigDecimal totalPrice = pricePerProduct.multiply(BigDecimal.valueOf(quantity));
        String status = orderDto.getStatus() != null ? orderDto.getStatus() : "PENDING";

        // Determine OrderHeader: if provided use it; otherwise, create a new one.
        OrderHeader orderHeader;

            orderHeader = OrderHeader.builder()
                    .orderDate(LocalDateTime.now())
                    .totalOrder(BigDecimal.ZERO)
                    .build();
            orderHeader = orderHeaderRepository.save(orderHeader);


        // Create OrderItem
        Order order = Order.builder()
                .customerId(orderDto.getCustomerId())
                .productId(orderDto.getProductId())
                .quantity(quantity)
                .pricePerProduct(pricePerProduct)
                .totalPrice(totalPrice)
                .status(status)
                .createdAt(LocalDateTime.now())
                .orderHeader(orderHeader)
                .build();
        order = orderRepository.save(order);

        // Update OrderHeader's totalOrder: recalculate the sum of totalPrice for all items in the header
        OrderHeader finalOrderHeader = orderHeader;
        List<Order> items = orderRepository.findAll().stream()
                .filter(item -> item.getOrderHeader().getId().equals(finalOrderHeader.getId()))
                .toList();
        BigDecimal sum = items.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderHeader.setTotalOrder(sum);
        orderHeaderRepository.save(orderHeader);

        return mapToDto(order);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        log.info("Retrieving order  with id {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order item not found with id: " + orderId));
        return mapToDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long orderId, String status) {
        log.info("Updating order item {} status to {}", orderId, status);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order item not found with id: " + orderId));
        order.setStatus(status);
        order = orderRepository.save(order);
        return mapToDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByCustomerId(Long customerId) {
        log.info("Retrieving order items for customer id {}", customerId);
        List<Order> orderItems = orderRepository.findByCustomerId(customerId);
        return orderItems.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private OrderDto mapToDto(Order orderItem) {
        return new OrderDto(
                orderItem.getId(),
                orderItem.getCustomerId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getPricePerProduct(),
                orderItem.getTotalPrice(),
                orderItem.getStatus(),
                orderItem.getCreatedAt()

        );
    }
}