package com.ecommerce.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderHeaderDto {
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private LocalDateTime orderShipment;
    private LocalDateTime orderDelivery;
    private BigDecimal totalOrder;
    private String status;
    private List<OrderDto> orderItems;
}