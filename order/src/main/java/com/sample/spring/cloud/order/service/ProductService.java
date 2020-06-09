package com.sample.spring.cloud.order.service;

import com.sample.spring.cloud.order.client.ProductClient;
import com.sample.spring.cloud.order.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductClient productClient;

//    @HystrixCommand(commandKey = "product-service.findByIds", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"),
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
//            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
//    })
    public List<Product> findProductByIds(List<Long> productIds) {
        List<Product> productList = productClient.findByIds(productIds);
        return productList;
    }
}
