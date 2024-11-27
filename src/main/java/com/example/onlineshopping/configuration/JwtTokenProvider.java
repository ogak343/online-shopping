//package com.example.onlineshopping.configuration;
//
//import reactor.core.publisher.Mono;
//
//public class JwtTokenProvider {
//    public Mono<Boolean> validateToken(String token) {
//        return Mono.just(token != null && token.startsWith("Bearer "));
//    }
//
//    public Mono<String> getUsernameFromToken(String token) {
//        return null;
//    }
//}
