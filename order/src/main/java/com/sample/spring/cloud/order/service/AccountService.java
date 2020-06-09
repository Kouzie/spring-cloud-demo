package com.sample.spring.cloud.order.service;

import com.sample.spring.cloud.order.client.AccountClient;
import com.sample.spring.cloud.order.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountClient accountClient;

//    @HystrixCommand(commandKey = "account-service.withdraw", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
//            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
//    })
    public Account withdraw(Long accountId, Integer amount) {
        return accountClient.withdrawById(accountId, amount);
    }
}
