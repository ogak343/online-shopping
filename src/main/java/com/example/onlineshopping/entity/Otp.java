package com.example.onlineshopping.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table(name = "otp")
@Setter
@Getter
public class Otp {
    @Id
    private Long id;
    private Long userId;
    private Long code;
    private Boolean confirmed;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiredAt;
}
