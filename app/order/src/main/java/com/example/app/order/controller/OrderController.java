package com.example.app.order.controller;

import com.example.app.order.model.Order;
import com.example.app.order.service.OrderService;
import com.example.common.dto.Account;
import com.example.common.dto.Customer;
import com.example.common.dto.OrderStatus;
import com.example.common.dto.Product;
import com.example.common.feign.client.AccountClient;
import com.example.common.feign.client.CustomerClient;
import com.example.common.feign.client.ProductClient;
import com.example.common.feign.client.ProductRequestLine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
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

    /*@PostMapping
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

    // OrderSender 를 통해 order 를 메세지 브로커로 전달. account-service 가 받아 처리
    @PostMapping
    public Order process(@RequestBody Order order) throws JsonProcessingException {
        order = orderService.add(order);
        log.info("Order saved: {}", mapper.writeValueAsString(order));
        return order;
    }*/

   /* @StreamListener(ProductOrder.INPUT)
    public void receiveProductOrder(Order order) throws JsonProcessingException {
        log.info("Order receiveProductOrder: {}", mapper.writeValueAsString(order));
        order = orderService.findById(order.getId());
        if (order.getStatus() != OrderStatus.REJECTED) {
            order.setStatus(order.getStatus());
            orderService.update(order);
            log.info("Order status updated: {}", mapper.writeValueAsString(order));
        }
    }

    @StreamListener(AccountOrder.INPUT)
    public void receiveAccountOrder(Order order) throws JsonProcessingException {
        log.info("Order receiveAccountOrder: {}", mapper.writeValueAsString(order));
        order = orderService.findById(order.getId());
        if (order.getStatus() != OrderStatus.REJECTED) {
            order.setStatus(order.getStatus());
            orderService.update(order);
            log.info("Order status updated: {}", mapper.writeValueAsString(order));
        }
    }*/

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
