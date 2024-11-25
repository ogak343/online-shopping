package com.example.onlineshopping.dto.request;

public record UserUpdateRequest(
        String username,
        String email,
        String phoneNumber,
        String firstName,
        String lastName,
        String middleName,
        String address,
        String city,
        String state
) {
}
