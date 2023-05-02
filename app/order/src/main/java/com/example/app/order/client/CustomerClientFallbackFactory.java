package com.example.app.order.client;

import com.example.common.feign.CustomerClient;
import com.example.common.dto.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerClientFallbackFactory {

    @Autowired
    private final CacheManager cacheManager;

}
