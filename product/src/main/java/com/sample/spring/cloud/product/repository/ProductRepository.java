package com.sample.spring.cloud.product.repository;

import com.sample.spring.cloud.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
