package com.example.onlineshopping.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PerformPaymentRequest(
        @NotNull UUID paymentId,
        @NotNull Long confirmCode
) {
}
