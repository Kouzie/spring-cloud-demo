package com.example.app.order.service;

import com.example.app.order.model.Order;
import com.example.app.order.repository.OrderRepository;
import com.example.common.dto.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @PostConstruct
    private void init() {
        for (long i = 0; i < 3; i++) {
            Order order = new Order();
            order.setAccountId(i+1);
            order.setCustomerId(i+1);
            order.setPrice(100);
            order.setStatus(OrderStatus.values()[(int) i]);
            orderRepository.save(order);
        }
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
