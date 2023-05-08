package com.example.app.account.repository;


import com.example.app.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByCustomerId(Long customerId);

    Optional<Account> findFirstByCustomerId(Long id);
}