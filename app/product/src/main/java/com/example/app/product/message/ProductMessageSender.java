//package com.example.app.product.message;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.stream.messaging.Processor;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ProductMessageSender {
//    private final Processor processor;
//
//    public boolean send(String payload) {
//        Message<String> message = MessageBuilder.withPayload(payload).build();
//        return processor.output().send(message);
//    }
//}
