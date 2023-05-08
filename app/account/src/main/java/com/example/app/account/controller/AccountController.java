package com.example.app.account.controller;


import com.example.app.account.model.Account;
import com.example.app.account.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final ObjectMapper mapper;
    private final AccountService accountService;

    @PostMapping
    public Account add(@RequestBody Account account) throws JsonProcessingException {
        log.info("Accounts add : {}", mapper.writeValueAsString(account));
        return accountService.add(account);
    }

    @PutMapping
    public Account update(@RequestBody Account account) throws JsonProcessingException {
        log.info("Accounts update : {}", mapper.writeValueAsString(account));
        return accountService.update(account);
    }

    @PutMapping("/withdraw/{id}/{amount}")
    public Account withdraw(@PathVariable("id") Long id, @PathVariable("amount") int amount) throws JsonProcessingException {
        Account account = accountService.findById(id);
        account.setBalance(account.getBalance() - amount);
        log.info("Accounts withdraw result: {}", mapper.writeValueAsString(account));
        return accountService.update(account);
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable("id") Long id) throws JsonProcessingException {
        Account account = accountService.findById(id);
        log.info("Accounts findById : {}", mapper.writeValueAsString(account));
        return account;
    }

    @GetMapping(value = "/customer/{customerId}", produces = "application/json; charset=utf-8")
    public List<Account> findByCustomerId(@PathVariable("customerId") Long customerId) throws JsonProcessingException {
        System.out.println("findByCustomerId invoke");
        List<Account> accounts = accountService.findAllByCustomerId(customerId);
        log.info("Accounts findByCustomerId : {}", mapper.writeValueAsString(accounts));
        return accounts;
    }

    @PostMapping("/ids")
    public List<Account> find(@RequestBody List<Long> ids) throws JsonProcessingException {
        List<Account> accounts = accountService.findAllById(ids);
        log.info("Accounts findByCustomerId : {}", mapper.writeValueAsString(accounts));

        return accounts;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("Accounts delete : {}", id);
        accountService.delete(id);
    }
}
