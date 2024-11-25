package com.example.onlineshopping.entity;

import com.example.onlineshopping.contants.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table
@Setter
@Getter
public class Order {
    @Id
    private Long id;
    private Long userId;
    private Long totalPrice;
    private OffsetDateTime createdDate;
    private OffsetDateTime paidDate;
    private OrderStatus status;
}
