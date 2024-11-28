package com.example.onlineshopping.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePaymentRequest(
        @NotNull UUID balanceId,
        @NotNull Long orderId
) {
}
