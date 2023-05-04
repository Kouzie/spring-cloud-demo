package com.example.app.order.client;

import com.example.common.dto.Account;
import com.example.common.feign.client.AccountClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountClientFallbackFactory {

    private final CacheManager cacheManager;

    public AccountClient create(Throwable throwable) {
        return new AccountClient() {
            @Override
            public Account withdraw(Long id, Integer amount) {
                log.info("withdrawById called" + throwable.getMessage());
                return new Account();
            }

            @Override
            public List<Account> findByCustomer(Long customerId) {
                log.info("findByCustomer called" + throwable.getMessage());
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
