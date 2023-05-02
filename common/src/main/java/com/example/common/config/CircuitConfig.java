package com.example.common.config;


import com.example.common.service.BackendService;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import io.github.resilience4j.core.RegistryStore;
import io.github.resilience4j.core.registry.InMemoryRegistryStore;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;


@Slf4j
@Configuration
public class CircuitConfig {

    public static void main(String[] args) {
        BackendService backendService = new BackendService();
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendService");
        circuitBreaker.getEventPublisher()
                .onSuccess(event -> System.out.println("circuitBreaker onSuccess invoked, " + event))
                .onError(event -> System.out.println("circuitBreaker onError invoked, " + event))
                .onIgnoredError(event -> System.out.println("circuitBreaker onIgnoredError invoked, " + event))
                .onReset(event -> System.out.println("circuitBreaker onReset invoked, " + event))
                .onStateTransition(event -> System.out.println("circuitBreaker onStateTransition invoked, " + event));

        Retry retry = Retry.ofDefaults("backendService");
        retry.getEventPublisher()
                .onRetry(event -> System.out.println("retry onRetry invoked, " + event))
                .onSuccess(event -> System.out.println("retry onSuccess invoked, " + event))
                .onError(event -> System.out.println("retry onError invoked, " + event))
                .onIgnoredError(event -> System.out.println("retry onIgnoredError invoked, " + event));

        Bulkhead bulkhead = Bulkhead.ofDefaults("backendService");
        bulkhead.getEventPublisher()
                .onCallPermitted(event -> System.out.println("bulkhead onCallPermitted invoked, " + event))
                .onCallRejected(event -> System.out.println("bulkhead onCallRejected invoked, " + event))
                .onCallFinished(event -> System.out.println("bulkhead onCallFinished invoked, " + event));
        Supplier<String> supplier = () -> backendService.doSomething();
        supplier = CircuitBreaker.decorateSupplier(circuitBreaker, supplier);
        supplier = Bulkhead.decorateSupplier(bulkhead, supplier);
        String result = supplier.get();
        System.out.println(result);
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        RegistryStore<CircuitBreaker> stores = new InMemoryRegistryStore<>();
        stores.putIfAbsent("backendA", CircuitBreaker.of("backendA", CircuitBreakerConfig
                .from(CircuitBreakerConfig.ofDefaults())
                .slidingWindowSize(10)
                .build()));
        stores.putIfAbsent("backendB", CircuitBreaker.of("backendB", CircuitBreakerConfig
                .from(CircuitBreakerConfig.ofDefaults())
                .slidingWindowSize(12)
                .build()));
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.custom()
                .withCircuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .withRegistryStore(stores)
                .build();
        return registry;
    }
}
