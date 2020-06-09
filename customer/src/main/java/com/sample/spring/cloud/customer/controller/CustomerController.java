package com.sample.spring.cloud.customer.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.spring.cloud.customer.client.AccountClient;
import com.sample.spring.cloud.customer.dto.Account;
import com.sample.spring.cloud.customer.model.Customer;
import com.sample.spring.cloud.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final ObjectMapper mapper;
    private final CustomerService customerService;
    private final AccountClient accountClient;

    @PostMapping
    public Customer add(@RequestBody Customer customer) throws JsonProcessingException {
        log.info("Customers add: {}", mapper.writeValueAsString(customer));
        return customerService.add(customer);
    }

    @PutMapping
    public Customer update(@RequestBody Customer customer) throws JsonProcessingException {
        log.info("Customers update: {}", mapper.writeValueAsString(customer));
        return customerService.update(customer);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Long id) throws JsonProcessingException {
        Customer customer = customerService.findById(id);
        log.info("Customers findById: {}", mapper.writeValueAsString(customer));
        return customer;
    }

    @GetMapping(value = "/withAccounts/{id}")
    public Customer findByIdWithAccounts(@PathVariable("id") Long id) throws JsonProcessingException {
        List<Account> accounts = accountClient.findCustomerAccounts(id);
        log.info("Customers findByIdWithAccounts accounts: {}", mapper.writeValueAsString(accounts));

        Customer customer = customerService.findById(id);
        log.info("Customers findByIdWithAccounts: {}", mapper.writeValueAsString(customer));

        customer.setAccounts(accounts);
        return customer;
    }

    @PostMapping("/ids")
    public List<Customer> find(@RequestBody List<Long> ids) throws JsonProcessingException {
        List<Customer> customers = customerService.find(ids);
        log.info("Customers find: {}", mapper.writeValueAsString(customers));
        return customers;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("Customers delete: {}", id);
        customerService.delete(id);
    }

}
