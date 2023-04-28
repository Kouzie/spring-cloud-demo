package com.sample.spring.cloud.customer.repository;


import com.sample.spring.cloud.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
