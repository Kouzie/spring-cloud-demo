package com.sample.spring.cloud.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.spring.cloud.product.model.Product;
import com.sample.spring.cloud.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ObjectMapper mapper;
    private final ProductService repository;

    @Value("${server.port}")
    String port;

    @PostMapping
    public Product add(@RequestBody Product product) throws JsonProcessingException {
        log.info("Products add : {}", mapper.writeValueAsString(product));
        return repository.add(product);
    }

    @PutMapping
    public Product update(@RequestBody Product product) throws JsonProcessingException {
        log.info("Products update : {}", mapper.writeValueAsString(product));
        return repository.update(product);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") Long id) throws JsonProcessingException {
        Product product = repository.findById(id);
        log.info("Products findById : {}", mapper.writeValueAsString(product));
        return product;
    }

    @PostMapping("/ids")
    public List<Product> find(@RequestBody List<Long> ids) {
        System.out.println("find invoke, port:" + port);
        return repository.find(ids);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("Products delete: {}", id);
        repository.delete(id);
    }

}
