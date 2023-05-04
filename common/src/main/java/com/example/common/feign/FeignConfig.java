package com.example.common.feign;

import com.example.common.dto.Product;
import com.example.common.exception.FeignRequestException;
import com.example.common.feign.client.ProductRequestLine;
import feign.FeignException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignConfig {
    /*@Bean
    ProductRequestLine productRequestLine(@Autowired ObjectFactory<HttpMessageConverters> messageConverters) {
        ProductRequestLine productRequestLine = Feign.builder()
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters))
                .target(ProductRequestLine.class, "http://localhost:8080/");
        return productRequestLine;
    }*/

    @Bean
    ProductRequestLine productService(@Autowired ObjectFactory<HttpMessageConverters> messageConverters) {
        // builder 순서대로 ordering 됨
        FeignDecorators decorators = FeignDecorators.builder()
                .withCircuitBreaker(CircuitBreaker.ofDefaults("product"))
                .withRateLimiter(RateLimiter.ofDefaults("product"))
                .withFallback(new ProductRequestLine() {
                    @Override
                    public Product findById(Long id) {
                        return new Product();
                    }
                }, FeignException.class)
                .build();
        return Resilience4jFeign.builder(decorators)
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters))
                .target(ProductRequestLine.class, "http://localhost:8080/");
    }

    @Bean
    RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            private static final String API_KEY = "api-key";
            private String apiKey = "my-api-key";

            // 모든 feign client 요청은 api-key 를 추가하여 전달
            @Override
            public void apply(RequestTemplate template) {
                log.info("add api key");
                template.header(API_KEY, apiKey);
            }
        };
    }

    @Bean
    ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            @Override
            public Exception decode(String methodKey, Response response) {
                log.error("feign error invoked, method:{}, status:{}, reason:{}", methodKey, response.status(), response.reason());
                return new FeignRequestException(response);
            }
        };
    }
}
