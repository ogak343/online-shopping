package com.example.onlineshopping.entity;

import com.example.onlineshopping.contants.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment")
@Setter
@Getter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long userId;
    private Long orderId;
    private UUID balanceToken;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    private OffsetDateTime paidAt;
    private Long amount;
    private Long confirmId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
