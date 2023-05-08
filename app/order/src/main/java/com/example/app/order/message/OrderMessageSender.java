//package com.example.app.order.message;
//
//import com.example.common.dto.Order;
//import com.example.common.dto.OrderStatus;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.integration.annotation.InboundChannelAdapter;
//import org.springframework.integration.annotation.Poller;
//import org.springframework.integration.core.MessageSource;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.support.GenericMessage;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.Random;
//
//@Slf4j
//@Service
//public class OrderMessageSender {
////    @Autowired
////    @Qualifier(AccountProducer.INPUT)
////    private SubscribableChannel accountSubscribableChannel;
////
////    @Autowired
////    @Qualifier(ProductProducer.INPUT)
////    private SubscribableChannel productSubscribableChannel;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @Autowired
//    @Qualifier(AccountProducer.OUTPUT)
//    private MessageChannel accountMessageChannel;
//
//    @Autowired
//    @Qualifier(ProductProducer.OUTPUT)
//    private MessageChannel productMessageChannel;
//
//    Random random = new Random();
//
//    public boolean sendToAccount(String payload) {
//        Message<String> msg = MessageBuilder
//                .withPayload(payload)
//                .build();
//        return accountMessageChannel.send(msg);
//    }
//
//    public boolean sendToProduct(String payload) {
//        Message<String> msg = MessageBuilder.withPayload(payload).build();
//        return productMessageChannel.send(msg);
//    }
//
//    @Bean
//    @InboundChannelAdapter(value = AccountProducer.OUTPUT, poller = @Poller(fixedDelay = "3000", maxMessagesPerPoll = "1"))
//    // org.springframework.integration.core.MessageSource
//    public MessageSource orderSource() {
//        log.info("orderSource invoked");
//        return new MessageSource() {
//            @Override
//            public Message receive() {
//                String result = "";
//                Order order = Order.builder()
//                        .status(OrderStatus.NEW)
//                        .accountId((long) random.nextInt(3))
//                        .customerId((long) random.nextInt(3))
//                        .productIds(Collections.singletonList((long) random.nextInt(3)))
//                        .build();
//                try {
//                    result = mapper.writeValueAsString(order);
//                } catch (JsonProcessingException e) {
//                    log.error(e.getMessage());
//                }
//                return new GenericMessage(result);
//            }
//        };
//    }
//}