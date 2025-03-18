package com.ecommerce.orders.repository;

import com.ecommerce.orders.model.Order;
import com.ecommerce.orders.model.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
    List<OrderHeader> findByCustomerId(Long customerId);
}
