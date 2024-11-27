package com.example.onlineshopping.repository;

import com.example.onlineshopping.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends R2dbcRepository<Product, Long> {
    Mono<Boolean> existsByCategoryId(Long categoryId);

    Flux<Product> findAllByCategoryId(Long categoryId);
}
