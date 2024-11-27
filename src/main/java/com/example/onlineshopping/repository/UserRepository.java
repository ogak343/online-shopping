package com.example.onlineshopping.repository;

import com.example.onlineshopping.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Long> {

    Mono<Boolean> existsByUsername(String username);
}
