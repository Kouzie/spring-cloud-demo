package com.example.app.customer.client;

import com.example.common.dto.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountClient {
    private final RestTemplate template;
    private final CacheManager cacheManager;

    // 서킷 브레이커와 연결된 프록시 객체로 둘러싼다.
    @CachePut("accounts")
    public List<Account> findCustomerAccounts(Long id) {
        Account[] accounts = template.getForObject("http://account-service/customer/{customerId}", Account[].class, id);
        return Arrays.stream(accounts).collect(Collectors.toList());
    }

    public List<Account> findCustomerAccountsFallback(Long id) {
        log.info("findCustomerAccountsFallback called");
//        return new ArrayList<>();
        ValueWrapper w = cacheManager.getCache("accounts").get(id);
        if (w != null) {
            log.info("call cacheManager");
            return (List<Account>) w.get();
        } else {
            return new ArrayList<>();
        }
    }
}
