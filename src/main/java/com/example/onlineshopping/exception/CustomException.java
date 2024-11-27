package com.example.onlineshopping.exception;

import com.example.onlineshopping.contants.ErrorCode;

public class CustomException extends RuntimeException {

    public CustomException(ErrorCode errorCode) {
    }
}
