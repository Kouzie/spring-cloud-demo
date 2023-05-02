package com.example.common.feign;


import com.example.common.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
    @PostMapping("/product/{ids}")
    List<Product> findByIds(@PathVariable List<Long> ids);
}