package com.example.app.customer.model;


import com.example.app.customer.dto.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private CustomerType type;

    @Transient
    private List<Account> accounts;

    public Customer(String name, CustomerType type) {
        this.name = name;
        this.type = type;
    }
}
