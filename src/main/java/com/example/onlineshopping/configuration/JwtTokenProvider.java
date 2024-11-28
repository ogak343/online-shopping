package com.example.onlineshopping.configuration;

import com.example.onlineshopping.dto.response.LoginResp;
import com.example.onlineshopping.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-duration}")
    private Long accessDuration;
    @Value("${jwt.refresh-duration}")
    private Long refreshDuration;

    public Long extractUserId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public LoginResp generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return new LoginResp(createToken(claims, user.getId(), accessDuration), createToken(claims, user.getId(), refreshDuration));
    }

    private String createToken(Map<String, Object> claims, Long userId, Long duration) {
        return Jwts.builder()
                .setClaims(claims).setSubject(String.valueOf(userId)).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(getKey())
                .compact();
    }

    public Boolean validateToken(String token, Long userId) {
        final Long extractedUsername = extractUserId(token);
        return (extractedUsername.equals(userId) && !isTokenExpired(token));
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}