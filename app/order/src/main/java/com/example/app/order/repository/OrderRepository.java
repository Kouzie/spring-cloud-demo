package com.example.app.order.repository;

import com.example.app.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    int countByCustomerId(Long customerId);
}