package com.example.onlineshopping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "balance")
@Setter
@Getter
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID token;
    private Long amount;
    private Long userId;
    private OffsetDateTime createdAt;
}
