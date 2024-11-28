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
    INVALID_TOKEN(403, "Invalid Token"),
    USER_UNVERIFIED(400, "User Unverified"),
    WRONG_CREDENTIALS(403, "Wrong Credentials"),
    INVALID_QUANTITY(400, "Invalid Quantity"),
    ORDER_NOT_FOUND(404, "Order Not Found"),
    PAYMENT_NOT_FOUND(404, "Payment Not Found"),
    INVALID_ORDER_STATUS(400, "Invalid Order Status"),
    BALANCE_NOT_FOUND(404, "Balance Not Found"),
    ACCESS_DENIED(403, "Access Denied"),
    NOT_ENOUGH_MONEY(400, "Not Enough Money"),;

    private final int statusCode;
    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
