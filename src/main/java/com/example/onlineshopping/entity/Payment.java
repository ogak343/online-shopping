package com.example.onlineshopping.entity;

import com.example.onlineshopping.contants.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Table(name = "payment")
@Setter
@Getter
public class Payment {
    private UUID id;
    private Long userId;
    private Long orderId;
    private OffsetDateTime createAt;
    private OffsetDateTime paidAt;
    private Long amount;
    private PaymentStatus status;
}
