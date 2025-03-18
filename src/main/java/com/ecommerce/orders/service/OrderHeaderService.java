package com.ecommerce.orders.service;

import com.ecommerce.orders.dto.OrderDto;
import com.ecommerce.orders.dto.OrderHeaderDto;

import java.util.List;

public interface OrderHeaderService {
    OrderHeaderDto createOrderHeader(OrderHeaderDto dto);
    OrderHeaderDto getOrderHeaderById(Long orderHeaderId);
    OrderHeaderDto updateOrderHeaderStatus(Long orderItemId, String status);
    List<OrderHeaderDto> getOrdersByCustomerId(Long customerId);
}
