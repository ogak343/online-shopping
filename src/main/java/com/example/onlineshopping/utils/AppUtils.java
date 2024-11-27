package com.example.onlineshopping.utils;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.exception.CustomException;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public class AppUtils {

    private AppUtils() {
    }

    public static Mono<Long> userId() {
//        return ReactiveSecurityContextHolder.getContext()
//                .map(authentication -> (Long) authentication.getAuthentication().getPrincipal())
//                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.INVALID_TOKEN)));
        return Mono.just(1L);
    }
}
