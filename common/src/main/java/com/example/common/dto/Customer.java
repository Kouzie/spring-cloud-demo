package com.example.common.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Long id;
    private Order name;
    private CustomerType type;
    private List<Account> accounts;
}