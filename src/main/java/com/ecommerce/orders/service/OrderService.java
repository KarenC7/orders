package com.ecommerce.orders.service;

import com.ecommerce.orders.dto.OrderDto;
import java.util.List;


public interface OrderService {
    OrderDto createOrder(OrderDto orderItemDto);
    OrderDto getOrderById(Long orderItemId);
    OrderDto updateOrderStatus(Long orderItemId, String status);
    List<OrderDto> getOrdersByCustomerId(Long customerId);
}