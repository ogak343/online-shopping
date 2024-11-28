package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.CreateCategoryRequest;
import com.example.onlineshopping.dto.request.UpdateCategoryRequest;
import com.example.onlineshopping.dto.response.CategoryResponse;
import com.example.onlineshopping.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CreateCategoryRequest request) {
        log.info("Create category request: {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CategoryResponse> update(@RequestBody @Valid UpdateCategoryRequest request,
                                                         @PathVariable Long id) {
        log.info("Update category request: {}, id: {}", request, id);
        return ResponseEntity.ok(service.update(request, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> get(@PathVariable Long id) {
        log.info("Get category request: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        log.info("Get all categories");
        return ResponseEntity.ok(service.getAll());
    }
}
