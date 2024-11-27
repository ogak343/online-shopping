package com.example.onlineshopping.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateCategoryRequest(
        @NotEmpty String name,
        @NotEmpty String description
) {
}
