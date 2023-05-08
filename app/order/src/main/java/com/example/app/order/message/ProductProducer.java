//package com.example.app.order.message;
//
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.SubscribableChannel;
//
//public interface ProductProducer {
//    // product -> order
//    String INPUT = "to-order-from-product";
//    // order -> product
//    String OUTPUT = "from-order-to-product";
//
//    @Input(INPUT)
//    SubscribableChannel subscribableChannel();
//
//    @Output(OUTPUT)
//    MessageChannel messageChannel();
//}