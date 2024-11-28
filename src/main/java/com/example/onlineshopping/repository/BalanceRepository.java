package com.example.onlineshopping.repository;

import com.example.onlineshopping.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BalanceRepository extends JpaRepository<Balance, UUID> {

    List<Balance> findAllByUserId(Long userId);
}
