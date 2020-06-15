package com.sample.spring.cloud.product.service;


import com.sample.spring.cloud.product.model.Product;
import com.sample.spring.cloud.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @PostConstruct
    @Transactional
    public void init() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Test1", 1000));
        productList.add(new Product("Test2", 1500));
        productList.add(new Product("Test3", 2000));
        productList.add(new Product("Test4", 3000));
        productList.add(new Product("Test5", 1300));
        productList.add(new Product("Test6", 2700));
        productList.add(new Product("Test7", 3500));
        productList.add(new Product("Test8", 1250));
        productList.add(new Product("Test9", 2450));
        productList.add(new Product("Test10", 800));
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
