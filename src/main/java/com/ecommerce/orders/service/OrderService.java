package com.ecommerce.orders.service;

import com.ecommerce.orders.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderById(Long orderId);
    OrderDto updateOrderStatus(Long orderId, String status);
    List<OrderDto> getOrdersByCustomerId(Long customerId);
}