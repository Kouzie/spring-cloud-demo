package com.sample.spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableZuulProxy
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                /*.route("account-service", new Function<PredicateSpec, AsyncBuilder>() {
                    @Override
                    public AsyncBuilder apply(PredicateSpec predicateSpec) {
                        return predicateSpec
                                .path("/api/account/**")
                                .filters(new Function<GatewayFilterSpec, UriSpec>() {
                                    @Override
                                    public UriSpec apply(GatewayFilterSpec gatewayFilterSpec) {
                                        return gatewayFilterSpec
                                                .prefixPath("/api")
                                                .rewritePath("/account/(?<path>.*)", "${path}");
                                    }
                                })
                                .uri("lb://account-service");
                    }
                })*/
                .route("customer-service", predicateSpec -> predicateSpec
                        .path("/api/customer/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .prefixPath("/api")
                                .rewritePath("/customer/(?<path>.*)", "${path}"))
                        .uri("lb://customer-service"))
                .route("order-service", predicateSpec -> predicateSpec
                        .path("/api/order/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .prefixPath("/api")
                                .rewritePath("/order/(?<path>.*)", "${path}"))
                        .uri("lb://order-service"))
                .route("product-service", predicateSpec -> predicateSpec
                        .path("/api/product/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .prefixPath("/api")
                                .rewritePath("/product/(?<path>.*)", "${path}"))
                        .uri("lb://product-service"))
                .build();
    }
}
