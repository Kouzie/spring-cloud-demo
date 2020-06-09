package com.sample.spring.cloud.account.service;

import com.sample.spring.cloud.account.model.Account;
import org.springframework.web.bind.annotation.*;

public interface AccountService {
    @PostMapping
    Account add(@RequestBody Account account);

    @PutMapping
    Account update(@RequestBody Account account);

    @PutMapping("/withdraw/{id}/{amount}")
    Account withdraw(@PathVariable("id") Long id, @PathVariable("amount") int amount);

    @GetMapping("/{id}")
    Account findById(@PathVariable("id") Long id);
}
