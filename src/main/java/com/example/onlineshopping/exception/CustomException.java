package com.example.onlineshopping.exception;

import com.example.onlineshopping.contants.ErrorCode;

public class CustomerException extends RuntimeException {
    public CustomerException(ErrorCode errorCode) {
    }
}
