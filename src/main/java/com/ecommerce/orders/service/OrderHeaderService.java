package com.ecommerce.orders.service;

import com.ecommerce.orders.dto.OrderHeaderDto;

public interface OrderHeaderService {
    OrderHeaderDto createOrderHeader(OrderHeaderDto dto);
    OrderHeaderDto getOrderHeaderById(Long orderHeaderId);
}
