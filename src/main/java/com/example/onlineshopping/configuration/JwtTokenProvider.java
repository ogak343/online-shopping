package com.example.onlineshopping.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Key;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    public Mono<Long> getUserIdFromToken(String token) {
        return Mono.just(Long.parseLong(
                Jwts.parserBuilder()
                        .setSigningKey(getKeyFromToken())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()));
    }

    private Key getKeyFromToken() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
