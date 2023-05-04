package com.example.common.feign.client;


import com.example.common.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

//@FeignClient(name = "product-service", fallback = ProductFallback.class)
@FeignClient(name = "product-service", fallbackFactory = ProductFallbackFactory.class)
public interface ProductClient {
    @PostMapping("/product/{ids}")
    List<Product> findByIds(@PathVariable List<Long> ids);
}


@Slf4j
@Component
class ProductFallback implements ProductClient {
    @Override
    public List<Product> findByIds(List<Long> ids) {
//        CircuitBreaker
        log.error("ProductFallback findByIds invoked, ids:{}", ids);
        List<Product> result = new ArrayList<>();
        result.add(new Product());
        return result;
    }
}

@Slf4j
@Component
class ProductFallbackFactory implements FallbackFactory<ProductClient> {

    @Override
    public ProductClient create(Throwable cause) {
        return new ProductClient() {
            @Override
            public List<Product> findByIds(List<Long> ids) {
                log.info("ProductFallbackFactory findByIds invoked error type:{}, msg:{}", cause.getClass().getSimpleName(), cause.getMessage());
                List<Product> result = new ArrayList<>();
                result.add(new Product());
                return result;
            }
        };
    }
}