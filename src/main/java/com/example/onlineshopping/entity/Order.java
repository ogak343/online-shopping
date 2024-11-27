package com.example.onlineshopping.entity;

import com.example.onlineshopping.contants.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table(name = "orders")
@Setter
@Getter
public class Order {
    @Id
    private Long id;
    private Long userId;
    private Long totalPrice;
    private OffsetDateTime createdAt;
    private OrderStatus status;
}
