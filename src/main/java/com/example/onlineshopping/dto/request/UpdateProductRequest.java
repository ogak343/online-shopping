package com.example.onlineshopping.dto.request;

import jakarta.validation.constraints.Min;

public record UpdateProductRequest(
        String name,
        String description,
        @Min(value = 1) Long price,
        @Min(value = 1) Integer quantity
) {
}
