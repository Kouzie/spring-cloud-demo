package com.sample.spring.cloud.account;

import com.sample.spring.cloud.account.model.Account;
import com.sample.spring.cloud.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class AccountApplication {
    @Value("${eureka.instance.metadata-map.zone}")
    String zonename;

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    @Bean
    AccountRepository repository() {
        AccountRepository repository = new AccountRepository();
        if (zonename.equals("zone1")) {
            repository.add(new Account("1234567890", 500000, 1L));
        } else {
            repository.add(new Account("1234567890", 200000, 1L));
        }
        repository.add(new Account("1234567891", 500000, 1L));
        repository.add(new Account("1234567892", 0, 1L));
        repository.add(new Account("1234567893", 500000, 2L));
        repository.add(new Account("1234567894", 0, 2L));
        repository.add(new Account("1234567895", 500000, 2L));
        repository.add(new Account("1234567896", 0, 3L));
        repository.add(new Account("1234567897", 500000, 3L));
        repository.add(new Account("1234567898", 500000, 3L));
        return repository;
    }

}
