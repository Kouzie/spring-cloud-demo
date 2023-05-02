package com.example.app.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private OrderStatus status;
    private int price;
    private Long customerId;
    private Long accountId;
    private List<Long> productIds;
}
