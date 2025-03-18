package com.ecommerce.orders.repository;

import com.ecommerce.orders.model.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
