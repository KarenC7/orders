package com.ecommerce.orders.service.impl;

import com.ecommerce.orders.adapter.FakeStoreApiClient;
import com.ecommerce.orders.dto.OrderDto;
import com.ecommerce.orders.model.Order;
import com.ecommerce.orders.model.OrderHeader;
import com.ecommerce.orders.dto.OrderHeaderDto;
import com.ecommerce.orders.exception.OrderNotFoundException;
import com.ecommerce.orders.repository.OrderHeaderRepository;
import com.ecommerce.orders.repository.OrderRepository;
import com.ecommerce.orders.service.OrderHeaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderHeaderServiceImpl implements OrderHeaderService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final OrderRepository orderItemRepository;
    private final FakeStoreApiClient fakeStoreApiClient;

    @Override
    @Transactional
    public OrderHeaderDto createOrderHeader(OrderHeaderDto dto) {
        log.info("Creating order header with {} order items", dto.getOrderItems().size());
        // Create new OrderHeader with current orderDate; orderShipment and orderDelivery come from dto (or null)
        OrderHeader header = OrderHeader.builder()
                .orderDate(LocalDateTime.now())
                .totalOrder(BigDecimal.ZERO)
                .build();
        header = orderHeaderRepository.save(header);

        List<Order> createdItems = new ArrayList<>();
        BigDecimal totalOrderSum = BigDecimal.ZERO;
        // Process each OrderItemCreateDto
        for (OrderDto itemDto : dto.getOrderItems()) {
            // Validate that the product exists and get its price
            var productDto = fakeStoreApiClient.getProductById(itemDto.getProductId());
            if (productDto == null) {
                throw new OrderNotFoundException("Product not found with id: " + itemDto.getProductId());
            }
            BigDecimal pricePerProduct = BigDecimal.valueOf(productDto.getPrice());
            BigDecimal totalPrice = pricePerProduct.multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            Order item = Order.builder()
                    .customerId(itemDto.getCustomerId())
                    .productId(itemDto.getProductId())
                    .quantity(itemDto.getQuantity())
                    .pricePerProduct(pricePerProduct)
                    .totalPrice(totalPrice)
                    .status(itemDto.getStatus() != null ? itemDto.getStatus() : "PENDING")
                    .createdAt(LocalDateTime.now())
                    .orderHeader(header)
                    .build();
            item = orderItemRepository.save(item);
            createdItems.add(item);
            totalOrderSum = totalOrderSum.add(totalPrice);
        }
        // Update header with order items and totalOrder sum
        header.setTotalOrder(totalOrderSum);
        header.setOrderItems(createdItems);
        orderHeaderRepository.save(header);
        return mapToDto(header);
    }

    @Override
    public OrderHeaderDto getOrderHeaderById(Long orderHeaderId) {
        OrderHeader header = orderHeaderRepository.findById(orderHeaderId)
                .orElseThrow(() -> new OrderNotFoundException("Order header not found with id: " + orderHeaderId));
        return mapToDto(header);
    }

    private OrderHeaderDto mapToDto(OrderHeader header) {
        List<OrderDto> itemDtos = header.getOrderItems() != null ?
                header.getOrderItems().stream().map(item -> new OrderDto(
                        item.getId(),
                        item.getCustomerId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPricePerProduct(),
                        item.getTotalPrice(),
                        item.getStatus(),
                        item.getCreatedAt()
                )).collect(Collectors.toList()) : null;
        return new OrderHeaderDto(
                header.getId(),
                header.getOrderDate(),
                header.getOrderShipment(),
                header.getOrderDelivery(),
                header.getTotalOrder(),
                itemDtos
        );
    }
}
