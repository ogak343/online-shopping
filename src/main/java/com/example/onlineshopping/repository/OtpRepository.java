package com.example.onlineshopping.repository;

import com.example.onlineshopping.entity.Otp;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface OtpRepository extends R2dbcRepository<Otp, Long> {
}
