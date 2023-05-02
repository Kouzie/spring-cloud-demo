package com.example.common.feign;


import com.example.common.dto.Customer;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @CachePut("customer")
    @GetMapping("/withAccounts/{id}")
    Customer findByIdWithAccounts(@PathVariable("id") Long id);
}