package com.example.app.product.message;

import com.example.app.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//@EnableBinding(Processor.class)
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductMessageHandler {

    private final ObjectMapper mapper;
    private final ProductService productService;
//    private final ProductMessageSender sender;
//
//    @StreamListener(Processor.INPUT)
//    public void receiveOrder(Order order) throws JsonProcessingException {
//        log.info("Order received: {}", mapper.writeValueAsString(order));
//        order.setStatus(OrderStatus.ACCEPTED);
//        log.info("Order response sent: {}", mapper.writeValueAsString(Collections.singletonMap("status", order.getStatus())));
//        sender.send(mapper.writeValueAsString(order));
//    }
}
