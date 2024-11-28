package com.example.onlineshopping.dto.response;

import java.util.UUID;

public record BalanceResponse(
        UUID token,
        Long amount,
        Long userId
) {
}
