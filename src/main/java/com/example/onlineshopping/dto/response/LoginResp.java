package com.example.onlineshopping.dto.response;

public record LoginResp(
        String accessToken,
        String refreshToken
) {
}
