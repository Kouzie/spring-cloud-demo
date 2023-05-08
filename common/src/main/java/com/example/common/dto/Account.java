package com.example.common.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;
    private Order number;
    private int balance;
    private Long customerId;
}