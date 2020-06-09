package com.sample.spring.cloud.customer.controller;


import com.sample.spring.cloud.customer.model.Account;
import com.sample.spring.cloud.customer.model.Customer;
import com.sample.spring.cloud.customer.repository.CustomerRepository;
import com.sample.spring.cloud.customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CustomerController {
    private static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    CustomerService service;

    @Autowired
    RestTemplate template;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping
    public Customer add(@RequestBody Customer customer) throws JsonProcessingException {
        LOGGER.info("Customers add: {}" ,mapper.writeValueAsString(customer));
        return service.add(customer);
    }

    @PutMapping
    public Customer update(@RequestBody Customer customer) throws JsonProcessingException {
        LOGGER.info("Customers update: {}" ,mapper.writeValueAsString(customer));
        return service.update(customer);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Long id) throws JsonProcessingException {
        Customer customer = service.findById(id);
        LOGGER.info("Customers findById: {}" ,mapper.writeValueAsString(customer));
        return customer;
    }

    @GetMapping(value = "/withAccounts/{id}")
    @ResponseBody
    public Customer findByIdWithAccounts(@PathVariable("id") Long id) throws JsonProcessingException {
        List<Account> accounts = service.findCustomerAccounts(id);
        LOGGER.info("Customers findByIdWithAccounts accounts: {}" ,mapper.writeValueAsString(accounts));

        Customer customer = customerRepository.findById(id);
        LOGGER.info("Customers findByIdWithAccounts: {}" ,mapper.writeValueAsString(customer));

        customer.setAccounts(accounts);
        return customer;
    }

    @PostMapping("/ids")
    public List<Customer> find(@RequestBody List<Long> ids) throws JsonProcessingException {
        List<Customer> customers = service.find(ids);
        LOGGER.info("Customers find: {}" ,mapper.writeValueAsString(customers));

        return customers;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Customers delete: {}" ,id);
        service.delete(id);
    }

}
