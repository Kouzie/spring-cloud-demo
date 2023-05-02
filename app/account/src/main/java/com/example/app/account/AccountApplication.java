package com.example.app.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class AccountApplication {
    @Value("${eureka.instance.metadata-map.zone}")
    String zonename;

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
