package com.example.common.feign.client;


import com.example.common.dto.Product;
import feign.Param;
import feign.RequestLine;

public interface ProductRequestLine {
    @RequestLine("GET /product/{id}")
    Product findById(@Param("id") Long id);
}