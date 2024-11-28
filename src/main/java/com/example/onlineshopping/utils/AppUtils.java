package com.example.onlineshopping.utils;


import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class AppUtils {

    private AppUtils() {
    }

    public static Long userId() {
        try {
            return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public static Optional<String> getHeader(String headerName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return Optional.ofNullable(request.getHeader(headerName));
        }
        return Optional.empty();
    }
}
