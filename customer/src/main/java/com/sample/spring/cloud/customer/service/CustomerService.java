package com.sample.spring.cloud.customer.service;

import com.sample.spring.cloud.customer.model.Account;
import com.sample.spring.cloud.customer.model.Customer;
import com.sample.spring.cloud.customer.repository.CustomerRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.cache.Cache.ValueWrapper;

@Service
public class CustomerService {
    private static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    RestTemplate template;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerRepository repository;

    @Autowired
    CacheManager cacheManager;

    public Customer add(Customer customer) {
        return repository.add(customer);
    }

    public Customer update(Customer customer) {
        return repository.update(customer);
    }

    public Customer findById(Long id) {
        return repository.findById(id);
    }

    public List<Customer> find(List<Long> ids) {
        return repository.find(ids);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    // 서킷 브레이커와 연결된 프록시 객체로 둘러싼다.
    @HystrixCommand(
            commandKey = "customer-service.findCustomerAccounts",
            fallbackMethod = "findCustomerAccountsFallback"
            , commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"), //실패시 500 ms간 호출하지 않음, 기본값 1000
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 서킷 장애를 지정할 최소 호출 실패 수, 기본값 20
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30"), // 최소 오류 비율. 기본값 50%
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"), // 서킷이 열린후 다시 제공할때 까지 모니터링 time, 기본값 10000
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000") // 통계의 롤링 간격
        }
    )
    @CachePut("accounts")
    public List<Account> findCustomerAccounts(Long id) {
        Account[] accounts = template.getForObject("http://account-service/customer/{customerId}", Account[].class, id);
        return Arrays.stream(accounts).collect(Collectors.toList());
    }

    public List<Account> findCustomerAccountsFallback(Long id) {
        LOGGER.info("findCustomerAccountsFallback called");
//        return new ArrayList<>();
        ValueWrapper w = cacheManager.getCache("accounts").get(id);
        if (w != null) {
            LOGGER.info("call cacheManager");
            return (List<Account>) w.get();
        } else {
            return new ArrayList<>();
        }
    }
}
