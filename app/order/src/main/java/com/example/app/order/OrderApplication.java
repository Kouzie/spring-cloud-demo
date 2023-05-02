package com.example.app.order;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication(scanBasePackages = "com.example")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.common")
//@EnableBinding(Processor.class)
public class OrderApplication {
    @Bean
    public CircuitBreakerConfigCustomizer testCustomizer() {
        return CircuitBreakerConfigCustomizer
                .of("backendA", builder -> builder.slidingWindowSize(12));
    }
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
