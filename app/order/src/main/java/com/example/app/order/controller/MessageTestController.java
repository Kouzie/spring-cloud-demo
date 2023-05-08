//package com.example.app.order.controller;
//
//import com.example.app.order.message.OrderMessageSender;
//import com.example.common.dto.Order;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/message")
//public class MessageTestController {
//    private final OrderMessageSender sender;
//    private final ObjectMapper mapper;
//
//    @PostMapping
//    public void sendMessage(@RequestBody Order orderDto) throws JsonProcessingException {
//        sender.sendToAccount(mapper.writeValueAsString(orderDto));
//        sender.sendToProduct(mapper.writeValueAsString(orderDto));
//    }
//}
