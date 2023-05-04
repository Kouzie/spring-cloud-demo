package com.example.app.customer.service;

import com.example.app.customer.model.Customer;
import com.example.app.customer.model.CustomerType;
import com.example.app.customer.repository.CustomerRepository;
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
public class CustomerService {

    private final CustomerRepository customerRepository;

    @PostConstruct
    @Transactional
    public void init() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer("John Scott", CustomerType.NEW));
        customerList.add(new Customer("Adam Smith", CustomerType.REGULAR));
        customerList.add(new Customer("Jacob Ryan", CustomerType.VIP));
        customerRepository.saveAll(customerList);
    }

    @Transactional
    public Customer add(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " customer is not exist"));
    }

    @Transactional(readOnly = true)
    public List<Customer> find(List<Long> ids) {
        return customerRepository.findAllById(ids);
    }
}
