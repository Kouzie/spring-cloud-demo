package com.sample.spring.cloud.order.client;

import com.sample.spring.cloud.order.model.Account;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountClientFallbackFactory implements FallbackFactory<AccountClient> {

    private static Logger LOGGER = LoggerFactory.getLogger(AccountClientFallbackFactory.class);

    @Autowired
    CacheManager cacheManager;

    @Override
    public AccountClient create(Throwable throwable) {
        return new AccountClient() {
            @Override
            public Account withdrawById(Long id, Integer amount) {
                LOGGER.info("withdrawById called" + throwable.getMessage());
                return new Account();
            }

            @Override
            public List<Account> findByCustomer(Long customerId) {
                LOGGER.info("findByCustomer called" + throwable.getMessage());
                Cache.ValueWrapper w = cacheManager.getCache("accounts").get(customerId);
                if (w != null) {
                    return (List<Account>) w.get();
                } else {
                    return new ArrayList<Account>();
                }
            }
        };
    }
}
