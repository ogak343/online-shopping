package com.example.onlineshopping.repository;

import com.example.onlineshopping.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User, Long> {

    boolean existsByUsername(String username);
}
