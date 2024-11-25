package com.example.onlineshopping.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record UserCreateRequest(
        @NotEmpty String username,
        @NotEmpty String password,
        @NotEmpty String email,
        String phoneNumber,
        String firstName,
        String lastName,
        String middleName,
        String address,
        String city,
        String state
) {
}
