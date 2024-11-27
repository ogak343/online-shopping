package com.example.onlineshopping.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(
        @NotNull @Min(value = 1) Long categoryId,
        @NotEmpty String name,
        String description,
        @NotNull @Min(value = 1) Long price,
        @NotNull @Min(value = 1) Integer quantity
) {
}
