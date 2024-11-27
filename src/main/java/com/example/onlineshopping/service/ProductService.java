package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.CreateProductRequest;
import com.example.onlineshopping.dto.request.UpdateProductRequest;
import com.example.onlineshopping.dto.response.ProductResponse;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.mapper.ProductMapper;
import com.example.onlineshopping.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository,
                          ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Mono<ProductResponse> create(CreateProductRequest request) {
        return repository.existsByCategoryId(request.categoryId())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) return repository.save(mapper.toEntity(request)).map(mapper::toResponse);
                    else return Mono.error(new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
                });
    }

    public Mono<ProductResponse> update(UpdateProductRequest request, Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.PRODUCT_NOT_FOUND)))
                .flatMap(product -> {
                    mapper.update(product, request);
                    return repository.save(product).map(mapper::toResponse);
                });
    }

    public Mono<ProductResponse> get(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.PRODUCT_NOT_FOUND)))
                .map(mapper::toResponse);
    }

    public Flux<ProductResponse> getCategoryProducts(Long categoryId) {
        return repository.findAllByCategoryId(categoryId)
                .map(mapper::toResponse);
    }
}
