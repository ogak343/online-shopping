package com.example.onlineshopping.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotEmpty List<OrderDetail> orderDetails
) {

    public record OrderDetail(
            @NotNull Long productId,
            @NotNull Integer quantity
    ) {
    }
}
