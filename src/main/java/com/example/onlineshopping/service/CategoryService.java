package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.CreateCategoryRequest;
import com.example.onlineshopping.dto.request.UpdateCategoryRequest;
import com.example.onlineshopping.dto.response.CategoryResponse;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.mapper.CategoryMapper;
import com.example.onlineshopping.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository,
                           CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Mono<CategoryResponse> create(CreateCategoryRequest request) {
        return repository.existsByName(request.name())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) return Mono.error(new CustomException(ErrorCode.CATEGORY_EXISTS));
                    else return repository.save(mapper.toEntity(request)).map(mapper::toResponse);
                });
    }

    public Mono<CategoryResponse> update(UpdateCategoryRequest request, Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.CATEGORY_NOT_FOUND)))
                .flatMap(entity -> {
                    mapper.update(entity, request);
                    return repository.save(entity).map(mapper::toResponse);
                });
    }

    public Mono<CategoryResponse> get(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.CATEGORY_NOT_FOUND)))
                .map(mapper::toResponse);
    }

    public Flux<CategoryResponse> getAll() {
        return repository.findAll()
                .map(mapper::toResponse)
                .doOnError(ex -> log.error(ex.getMessage(), ex));
    }
}
