package com.example.onlineshopping.dto.response;

import com.example.onlineshopping.contants.Role;

public record UserResponse(
        Long id,
        String username,
        String email,
        String phoneNumber,
        String firstName,
        String lastName,
        String middleName,
        Role role,
        String address,
        String city,
        String state
) {
}
