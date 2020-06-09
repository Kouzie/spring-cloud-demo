package com.sample.spring.cloud.order.service;

import com.sample.spring.cloud.order.model.Order;
import com.sample.spring.cloud.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order add(Order order) {
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " order is not exist"));
    }

    public Order update(Order order) {
        return orderRepository.save(order);
    }

    public int countByCustomerId(Long customerId) {
        return orderRepository.countByCustomerId(customerId);
    }
}
