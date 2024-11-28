package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.CreateProductRequest;
import com.example.onlineshopping.dto.request.UpdateProductRequest;
import com.example.onlineshopping.dto.response.ProductResponse;
import com.example.onlineshopping.service.ProductService;
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
@RequestMapping("/products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid CreateProductRequest request) {
        log.info("Creating product {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductResponse> update(@RequestBody @Valid UpdateProductRequest request,
                                                        @PathVariable("id") Long id) {
        log.info("Updating product {}", request);
        return ResponseEntity.ok(service.update(request, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable("id") Long id) {
        log.info("Getting product {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductResponse>> getCategoryProducts(@PathVariable("id") Long categoryId) {
        log.info("[Getting category products] categoryId: {}", categoryId);
        return ResponseEntity.ok(service.getCategoryProducts(categoryId));
    }

}
