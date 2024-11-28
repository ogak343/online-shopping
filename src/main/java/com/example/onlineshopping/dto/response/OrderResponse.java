package com.example.onlineshopping.dto.response;

import com.example.onlineshopping.contants.OrderStatus;

import java.util.List;

public record OrderResponse(
        Long id,
        Long userId,
        List<OrderDetailResponse> orderDetails,
        Long totalPrice,
        OrderStatus status
) {
    public record OrderDetailResponse(
            Long id,
            Long orderId,
            Long productId,
            Integer quantity
    ) {
    }
}
