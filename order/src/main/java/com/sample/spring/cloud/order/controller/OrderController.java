package com.sample.spring.cloud.order.controller;

import com.example.order.model.*;
import com.sample.spring.cloud.order.repository.OrderRepository;
import com.sample.spring.cloud.order.service.AccountService;
import com.sample.spring.cloud.order.service.CustomerService;
import com.sample.spring.cloud.order.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.spring.cloud.order.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
public class OrderController {

    private static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public Order prepare(@RequestBody Order order) throws JsonProcessingException {
        int price = 0;
        List<Product> products = productService.findProductByIds(order.getProductIds());
        LOGGER.info("Products found: {}" ,mapper.writeValueAsString(products));

        Customer customer = customerService.findCustomerWithAccounts(order.getCustomerId());
        LOGGER.info("Customer found: {}" ,mapper.writeValueAsString(customer));

        for (Product product : products)
            price += product.getPrice();
        final int priceDiscounted = priceDiscount(price, customer);
        LOGGER.info("Discounted price: {}" ,mapper.writeValueAsString(Collections.singletonMap("price", priceDiscounted)));
        Optional<Account> account = customer.getAccounts().stream().filter(a -> (a.getBalance() > priceDiscounted)).findFirst();
        if (account.isPresent()) {
            order.setAccountId(account.get().getId());
            order.setStatus(OrderStatus.ACCEPTED);
            order.setPrice(priceDiscounted);
            LOGGER.info("Account found: {}" ,mapper.writeValueAsString(account.get()));
        } else {
            order.setStatus(OrderStatus.REJECTED);
            LOGGER.info("Account not found: {}" ,mapper.writeValueAsString(customer.getAccounts()));
        }
        return orderRepository.add(order);
    }

    @PutMapping("/{id}")
    public Order accept(@PathVariable Long id) {
        final Order order = orderRepository.findById(id);
        //restTemplate.put("http://account-service/withdraw/{id}/{amount}", null, order.getAccountId(), order.getPrice());
        Account account = accountService.withdraw(order.getAccountId(), order.getPrice());
        order.setStatus(OrderStatus.DONE);
        orderRepository.update(order);
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
        int ordersNum = orderRepository.countByCustomerId(customer.getId());
        discount += (ordersNum * 0.01);
        return (int) (price - (price * discount));
    }
}
