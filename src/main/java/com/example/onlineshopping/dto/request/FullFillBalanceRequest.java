package com.example.onlineshopping.dto.request;

import java.util.UUID;

public record FullFillBalanceRequest(
        UUID balanceId,
        Long amount
) {
}
