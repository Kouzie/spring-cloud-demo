package com.example.app.product.service;


import com.example.app.product.repository.ProductRepository;
import com.example.app.product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @PostConstruct
    @Transactional
    public void init() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Test1", 1000, 10));
        productList.add(new Product("Test2", 1500, 10));
        productList.add(new Product("Test3", 2000, 20));
        productList.add(new Product("Test4", 3000, 20));
        productList.add(new Product("Test5", 1300, 10));
        productList.add(new Product("Test6", 2700, 20));
        productList.add(new Product("Test7", 3500, 40));
        productList.add(new Product("Test8", 1250, 30));
        productList.add(new Product("Test9", 2450, 10));
        productList.add(new Product("Test10", 800, 20));
        productRepository.saveAll(productList);
    }

    @Transactional
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " product is not exist"));
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> find(List<Long> ids) {
        return productRepository.findAllById(ids);
    }
}
