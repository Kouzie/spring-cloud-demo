package com.example.app.account.message;

import com.example.app.account.model.Account;
import com.example.app.account.service.AccountService;
import com.example.common.dto.Order;
import com.example.common.dto.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

//@EnableBinding(Processor.class)
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountMessageHandler {

    private final ObjectMapper mapper;
    private final AccountService accountService;

    /*@StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Order receiveOrder(String msg) throws JsonProcessingException {
        log.info("received msg:{}", msg);
        Order order = mapper.readValue(msg, Order.class);
        log.info("Order received: {}", (order));
        Account account = accountService.findById(order.getAccountId());
        log.info("Account found: {}", mapper.writeValueAsString(account));
        order.setStatus(OrderStatus.ACCEPTED);
        return order;
    }*/

    @Bean(name = "account-producer")
    public Function<String, Order> inputAndOutput() {
        return new Function<String, Order>() {
            @Override
            public Order apply(String msg) {
                log.info("received msg:{}", msg);
                try {
                    Order order = mapper.readValue(msg, Order.class);
                    log.info("Order received: {}", (order));
                    Account account = accountService.findById(order.getAccountId());
                    log.info("Account found: {}", mapper.writeValueAsString(account));
                    order.setStatus(OrderStatus.ACCEPTED);
                    return order;
                } catch (Exception e) {
                    log.error("input error invoked, error type:{}, msg:{}", e.getClass().getSimpleName(), e.getMessage());
                    return null;
                }
            }
        };
    }
//
//    @Bean(name = "account-sink")
//    public Consumer<String> input() {
//        return new Consumer<String>() {
//            @Override
//            public void accept(String msg) {
//                System.out.println("Received: " + msg);
//            }
//        };
//    }

//    @Bean
//    public Function<String, String> uppercase() {
//        return value -> value.toUpperCase();
//    }
//
//    @Bean
//    public Function<String, String> reverse() {
//        return value -> new StringBuilder(value).reverse().toString();
//    }
}
