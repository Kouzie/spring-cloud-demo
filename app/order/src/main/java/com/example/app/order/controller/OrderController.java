package com.example.app.order.controller;

import com.example.app.order.message.OrderMessageSender;
import com.example.app.order.model.Order;
import com.example.app.order.service.OrderService;
import com.example.common.dto.*;
import com.example.common.feign.client.AccountClient;
import com.example.common.feign.client.CustomerClient;
import com.example.common.feign.client.ProductClient;
import com.example.common.feign.client.ProductRequestLine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderMessageSender orderSender;
    private final ObjectMapper mapper;

    private final OrderService orderService;
    private final AccountClient accountClient;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final ProductRequestLine productService;
    @Autowired
    @LoadBalanced
    private RestTemplate loadBalanced;

    @GetMapping("/test")
    public String testMethod() {
        String result = loadBalanced.getForObject("http://product-service/product/test", String.class);
        return result;
    }

    @GetMapping("/product/{productId}/line")
    public Product testLineMethod(@PathVariable Long productId) {
        Product result = productService.findById(productId);
        return result;
    }

    @GetMapping("/product/{productId}")
    public List<Product> getProductById(@PathVariable Long productId) {
        List<Product> products = productClient.findByIds(Collections.singletonList(productId));
        return products;
    }

    @PostMapping
    public Order prepare(@RequestBody Order order) throws JsonProcessingException {
        List<Product> products = productClient.findByIds(order.getProductIds());
        log.info("Products found: {}", mapper.writeValueAsString(products));

        Customer customer = customerClient.findByIdWithAccounts(order.getCustomerId());
        log.info("Customer found: {}", mapper.writeValueAsString(customer));

        int price = products.stream().mapToInt(Product::getPrice).sum();
        final int priceDiscounted = priceDiscount(price, customer); // 해당 customer 주문횟수만큼 discount
        log.info("Discounted price: {}", mapper.writeValueAsString(Collections.singletonMap("price", priceDiscounted)));
        Optional<Account> account = customer.getAccounts().stream()
                .filter(a -> (a.getBalance() > priceDiscounted)).findFirst();
        if (account.isPresent()) {
            order.setAccountId(account.get().getId());
            order.setStatus(OrderStatus.ACCEPTED);
            order.setPrice(priceDiscounted);
            log.info("Account found: {}", mapper.writeValueAsString(account.get()));
        } else {
            order.setStatus(OrderStatus.REJECTED);
            log.info("Account not found: {}", mapper.writeValueAsString(customer.getAccounts()));
        }
        return orderService.add(order);
    }

    @PutMapping("/{orderId}")
    public Order accept(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        Account account = accountClient.withdraw(order.getAccountId(), order.getPrice());
        log.info("withdraw result:" + account.toString());
        order.setStatus(OrderStatus.DONE);
        order = orderService.update(order);
        return order;
    }

    private int priceDiscount(int price, Customer customer) {
        double discount = 0;
        switch (customer.getType()) {
            case REGULAR:
                discount += 0.05;
                break;
            case VIP:
                discount += 0.1;
                break;
            default:
                break;
        }
        int ordersNum = orderService.countByCustomerId(customer.getId());
        discount += (ordersNum * 0.01);
        return (int) (price - (price * discount));
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        Order order = orderService.findById(id);
        Customer customer = customerClient.findByIdWithAccounts(order.getAccountId());
        log.info("customer:" + customer.toString());
        return order;
    }
}
