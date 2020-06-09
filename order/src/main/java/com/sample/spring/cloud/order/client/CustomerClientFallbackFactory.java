package com.sample.spring.cloud.order.client;

import com.sample.spring.cloud.order.model.Customer;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CustomerClientFallbackFactory implements FallbackFactory<CustomerClient> {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomerClientFallbackFactory.class);

    @Autowired
    CacheManager cacheManager;

    @Override
    public CustomerClient create(Throwable throwable) {
        return new CustomerClient() {
            @Override
            public Customer findByIdWithAccounts(Long customerId) {
                LOGGER.info("findByIdWithAccounts called" + throwable.getMessage());
                Cache.ValueWrapper w = cacheManager.getCache("customers").get(customerId);
                if (w != null) {
                    return (Customer) w.get();
                } else {
                    return new Customer();
                }
            }
        };
    }
}
