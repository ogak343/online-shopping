package com.example.onlineshopping.dto.response;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Long price,
        Integer quantity,
        Long categoryId
) {
}
