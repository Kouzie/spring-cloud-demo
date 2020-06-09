package com.sample.spring.cloud.customer.repository;


import com.sample.spring.cloud.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
