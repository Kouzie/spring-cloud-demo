package com.sample.spring.cloud.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.spring.cloud.product.dto.Order;
import com.sample.spring.cloud.product.dto.OrderStatus;
import com.sample.spring.cloud.product.message.OrderSender;
import com.sample.spring.cloud.product.model.Product;
import com.sample.spring.cloud.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ObjectMapper mapper;
    private final ProductService productService;
    private final OrderSender orderSender;

    @Value("${server.port}")
    String port;

    @StreamListener(Processor.INPUT)
    public void receiveOrder(Order order) throws JsonProcessingException {
        log.info("Order received: {}", mapper.writeValueAsString(order));
        for (Long productId : order.getProductIds()) {
            Product product = productService.findById(productId);
            if (product.getCount() == 0) {
                order.setStatus(OrderStatus.REJECTED);
                break;
            }
            product.setCount(product.getCount() - 1);
            productService.update(product);
            log.info("Product updated: {}", mapper.writeValueAsString(product));
        }
        if (order.getStatus() != OrderStatus.REJECTED) {
            order.setStatus(OrderStatus.ACCEPTED);
        }
        log.info("Order response sent: {}", mapper.writeValueAsString(Collections.singletonMap("status", order.getStatus())));
        orderSender.send(order);
    }

    @PostMapping
    public Product add(@RequestBody Product product) throws JsonProcessingException {
        log.info("Products add : {}", mapper.writeValueAsString(product));
        return productService.add(product);
    }

    @PutMapping
    public Product update(@RequestBody Product product) throws JsonProcessingException {
        log.info("Products update : {}", mapper.writeValueAsString(product));
        return productService.update(product);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") Long id) throws JsonProcessingException {
        Product product = productService.findById(id);
        log.info("Products findById : {}", mapper.writeValueAsString(product));
        return product;
    }

    @PostMapping("/{ids}")
    public List<Product> find(@PathVariable List<Long> ids) {
        System.out.println("find invoke, port:" + port);
        return productService.find(ids);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("Products delete: {}", id);
        productService.delete(id);
    }
}
