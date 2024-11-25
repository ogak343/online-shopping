package com.example.onlineshopping.dto.request;

public record UserConfirmRequest(
        Long otpId,
        Long otpCode
) {
}
