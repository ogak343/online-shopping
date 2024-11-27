package com.example.onlineshopping.repository;

import com.example.onlineshopping.entity.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends R2dbcRepository<Category, Long> {
    Mono<Boolean> existsByName(String name);
}
