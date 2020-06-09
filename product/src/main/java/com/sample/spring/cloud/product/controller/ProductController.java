package com.sample.spring.cloud.product.controller;

import com.sample.spring.cloud.product.model.Product;
import com.sample.spring.cloud.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ProductController {
    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    ProductService repository;

    @Value("${server.port}")
    String port;

    @PostMapping
    public Product add(@RequestBody Product product) throws JsonProcessingException {
        LOGGER.info("Products add : {}", mapper.writeValueAsString(product));
        return repository.add(product);
    }

    @PutMapping
    public Product update(@RequestBody Product product) throws JsonProcessingException {
        LOGGER.info("Products update : {}", mapper.writeValueAsString(product));
        return repository.update(product);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") Long id) throws JsonProcessingException {
        Product product = repository.findById(id);
        LOGGER.info("Products findById : {}", mapper.writeValueAsString(product));
        return product;
    }

    @PostMapping("/ids")
    public List<Product> find(@RequestBody List<Long> ids) {
        System.out.println("find invoke, port:" +  port);
        return repository.find(ids);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Products delete: {}", id);
        repository.delete(id);
    }

}
