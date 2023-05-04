package com.example.common.loadbalacner;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * @LoadBalancerClient configuration 속성으로 알고리즘 변경 가능
 * default org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration
 */
@Configuration
@LoadBalancerClient(name = "demo-lb", configuration = CustomLoadBalancerConfiguration.class)
public class LoadBalancerConfig {
    @LoadBalanced
    @Bean
    RestTemplate loadBalanced() {
        return new RestTemplate();
    }

    @Primary
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new ServiceInstanceListSupplier() {
            @Override
            public String getServiceId() {
                return "product-service";
            }

            @Override
            public Flux<List<ServiceInstance>> get() {
                return Flux.just(Arrays.asList(
                        new DefaultServiceInstance("product-service-1", "product-service", "localhost", 8082, false),
                        new DefaultServiceInstance("product-service-2", "product-service", "localhost", 8083, false)
                ));
            }
        };
    }
    */
}