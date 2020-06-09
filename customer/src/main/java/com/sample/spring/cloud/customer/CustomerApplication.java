package com.sample.spring.cloud.customer;

import com.sample.spring.cloud.customer.model.Customer;
import com.sample.spring.cloud.customer.model.CustomerType;
import com.sample.spring.cloud.customer.repository.CustomerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableHystrix
//@EnableHystrixDashboard
@RibbonClient(name = "account-service")
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CustomerRepository repository() {
        CustomerRepository repository = new CustomerRepository();
        repository.add(new Customer("John Scott", CustomerType.NEW));
        repository.add(new Customer("Adam Smith", CustomerType.REGULAR));
        repository.add(new Customer("Jacob Ryan", CustomerType.VIP));
        return repository;
    }
}

