package com.sample.spring.cloud.account.controller;


import com.sample.spring.cloud.account.model.Account;
import com.sample.spring.cloud.account.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    private static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    AccountRepository repository;

    @Value("${server.port}")
    String port;

    @PostMapping
    public Account add(@RequestBody Account account) throws JsonProcessingException {
        LOGGER.info("Accounts add : {}", mapper.writeValueAsString(account));
        return repository.add(account);
    }

    @PutMapping
    public Account update(@RequestBody Account account) throws JsonProcessingException {
        LOGGER.info("Accounts update : {}", mapper.writeValueAsString(account));
        return repository.update(account);
    }

    @PutMapping("/withdraw/{id}/{amount}")
    public Account withdraw(@PathVariable("id") Long id, @PathVariable("amount") int amount) throws JsonProcessingException {
        Account account = repository.findById(id);
        account.setBalance(account.getBalance() - amount);
        LOGGER.info("Accounts withdraw result: {}", mapper.writeValueAsString(account));
        return repository.update(account);
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable("id") Long id) throws JsonProcessingException {
        Account account = repository.findById(id);
        LOGGER.info("Accounts findById : {}", mapper.writeValueAsString(account));
        return account;
    }

    @GetMapping(value = "/customer/{customerId}", produces = "application/json; charset=utf-8")
    public List<Account> findByCustomerId(@PathVariable("customerId") Long customerId) throws JsonProcessingException {
        System.out.println("findByCustomerId invode, port:" + port);
        List<Account> accounts = repository.findByCustomer(customerId);
        LOGGER.info("Accounts findByCustomerId : {}", mapper.writeValueAsString(accounts));

        return accounts;
    }

    @PostMapping("/ids")
    public List<Account> find(@RequestBody List<Long> ids) throws JsonProcessingException {
        List<Account> accounts = repository.find(ids);
        LOGGER.info("Accounts findByCustomerId : {}", mapper.writeValueAsString(accounts));

        return accounts;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Accounts delete : {}", id);
        repository.delete(id);
    }

}
