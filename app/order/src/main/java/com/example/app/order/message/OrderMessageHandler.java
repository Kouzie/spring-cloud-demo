package com.example.app.order.message;

import com.example.app.order.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

//@EnableBinding({AccountProducer.class, ProductProducer.class})
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageHandler {

    private final ObjectMapper mapper;

    @StreamListener(ProductProducer.INPUT)
    public void receiveProductOrder(Order order) throws JsonProcessingException {
        log.info("Order receiveProductOrder: {}", mapper.writeValueAsString(order));
    }

    @StreamListener(AccountProducer.INPUT)
    public void receiveAccountOrder(Order order) throws JsonProcessingException {
        log.info("Order receiveAccountOrder: {}", mapper.writeValueAsString(order));
    }
}
