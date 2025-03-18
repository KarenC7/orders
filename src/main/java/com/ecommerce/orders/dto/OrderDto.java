package com.ecommerce.orders.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

}
