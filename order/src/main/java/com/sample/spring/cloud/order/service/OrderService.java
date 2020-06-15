package com.sample.spring.cloud.order.service;

import com.sample.spring.cloud.order.model.Order;
import com.sample.spring.cloud.order.model.OrderStatus;
import com.sample.spring.cloud.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @PostConstruct
    private void init() {
        Order order = new Order();
        order.setAccountId(1l);
        order.setCustomerId(1l);
        order.setPrice(100);
        order.setStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }

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
