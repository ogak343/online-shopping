package com.example.onlineshopping.mapper;

import com.example.onlineshopping.dto.response.BalanceResponse;
import com.example.onlineshopping.entity.Balance;
import org.springframework.stereotype.Component;

@Component
public class BalanceMapper {

    public BalanceResponse toResponse(Balance balance) {
        return new BalanceResponse(balance.getToken(), balance.getAmount(), balance.getUserId());
    }
}
