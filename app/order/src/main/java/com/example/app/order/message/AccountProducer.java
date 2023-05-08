//package com.example.app.order.message;
//
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.SubscribableChannel;
//
//public interface AccountProducer {
//    // account -> order
//    String INPUT = "to-order-from-account";
//    // order -> account
//    String OUTPUT = "from-order-to-account";
//
//    @Input(INPUT)
//    SubscribableChannel subscribableChannel();
//
//    @Output(OUTPUT)
//    MessageChannel messageChannel();
//}