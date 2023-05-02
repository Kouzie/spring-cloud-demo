package com.example.app.customer.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private String number;
    private int balance;
    private Long customerId;
}
