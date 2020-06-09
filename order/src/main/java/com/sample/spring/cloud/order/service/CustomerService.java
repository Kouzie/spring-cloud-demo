package com.sample.spring.cloud.order.service;

import com.sample.spring.cloud.order.client.CustomerClient;
import com.sample.spring.cloud.order.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service

public class CustomerService {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    CustomerClient customerClient;

    @CachePut("customers")
//    @HystrixCommand(
//            commandKey = "customer-service.findCustomerWithAccounts",
//            fallbackMethod = "findCustomerWithAccountsFallback",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
//                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
//            }
//    )
    public Customer findCustomerWithAccounts(Long customerId) {
//        Customer customer = template.getForObject("http://customer-service/withAccounts/{id}", Customer.class, customerId);
        Customer customer = customerClient.findByIdWithAccounts(customerId);
        return customer;
    }

//    public Customer findCustomerWithAccountsFallback(Long customerId) {
//        Cache.ValueWrapper w = cacheManager.getCache("customers").get(customerId);
//        if (w != null) {
//            return (Customer) w.get();
//        } else {
//            return new Customer();
//        }
//    }
}
