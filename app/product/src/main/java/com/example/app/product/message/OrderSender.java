//package com.sample.spring.cloud.product.message;
//
//
//import com.sample.spring.cloud.product.dto.Order;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.stream.messaging.Source;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class OrderSender {
//    private final Source source;
//
//    public boolean send(Order order) {
//        return source.output().send(MessageBuilder.withPayload(order).build());
//    }
//}
