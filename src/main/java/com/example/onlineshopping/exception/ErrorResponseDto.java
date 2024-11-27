package com.example.onlineshopping.exception;

import com.example.onlineshopping.contants.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseDto {
    private final int code;
    private final String message;
    private List<String> fieldErrors = new ArrayList<>();

    public ErrorResponseDto(ErrorCode errorCode) {
        this.code = errorCode.getStatusCode();
        this.message = errorCode.getMessage();
    }

    public ErrorResponseDto(HttpStatus httpStatus, List<String> fieldErrors) {
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.fieldErrors = fieldErrors;
    }
}
