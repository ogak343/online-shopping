package com.example.onlineshopping.contants;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(404, "User Not Found"),
    NOTIFICATION_FAILED(500, "Notification Failed"),
    OTP_NOT_FOUND(404, "OTP Not Found"),
    INVALID_OTP(400, "Invalid OTP"),
    INVALID_OTP_CODE(400, "Invalid OTP Code"),
    CATEGORY_EXISTS(409, "Category Already Exists"),
    CATEGORY_NOT_FOUND(404, "Category Not Found"),
    PRODUCT_NOT_FOUND(404, "Product Not Found"),
    USER_EXISTS(409, "User Already Exists"),
    INVALID_TOKEN(403, "Invalid Token"),;

    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
