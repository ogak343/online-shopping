package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.CreateCategoryRequest;
import com.example.onlineshopping.dto.request.UpdateCategoryRequest;
import com.example.onlineshopping.dto.response.CategoryResponse;
import com.example.onlineshopping.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<CategoryResponse>> create(@RequestBody @Valid CreateCategoryRequest request) {
        log.info("Create category request: {}", request);
        return service.create(request).map(ResponseEntity::ok);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<CategoryResponse>> update(@RequestBody @Valid UpdateCategoryRequest request,
                                                         @PathVariable Long id) {
        log.info("Update category request: {}, id: {}", request, id);
        return service.update(request, id).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CategoryResponse>> get(@PathVariable Long id) {
        log.info("Get category request: {}", id);
        return service.get(id).map(ResponseEntity::ok);
    }

    @GetMapping
    public ResponseEntity<Flux<CategoryResponse>> getAll() {
        log.info("Get all categories");
        return ResponseEntity.ok(service.getAll());
    }
}
