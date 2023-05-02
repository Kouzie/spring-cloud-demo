package com.sample.spring.cloud.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    //spring.cloud.gateway.routes[0].id=order-service
    //spring.cloud.gateway.routes[0].uri=http://localhost:8082
    //spring.cloud.gateway.routes[0].predicates[0]=Path=/api/order/**
    //spring.cloud.gateway.routes[0].filters[0]=RewritePath=/api/order/(?<path>.*), /$\\{path}
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("order-service", predicateSpec -> predicateSpec
                        .path("/api/order/**")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec
                                .rewritePath("/api/order/(?<path>.*)", "/${path}"))
                        .uri("http://localhost:8082"))
                .build();
    }
}
