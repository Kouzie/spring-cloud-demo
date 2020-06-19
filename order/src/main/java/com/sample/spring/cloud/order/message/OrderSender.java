package com.sample.spring.cloud.order.message;

import com.sample.spring.cloud.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSender {
    private final AccountOrderSender accountOrderSender;
    private final ProductOrderSender productOrderSender;

    public boolean send(Order order) {
        boolean result1 = accountOrderSender.send(order);
        boolean result2 = productOrderSender.send(order);
        return result1 && result2;
    }
}
