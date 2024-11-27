package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.CreateProductRequest;
import com.example.onlineshopping.dto.request.UpdateProductRequest;
import com.example.onlineshopping.dto.response.ProductResponse;
import com.example.onlineshopping.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<ProductResponse>> create(@RequestBody @Valid CreateProductRequest request) {
        log.info("Creating product {}", request);
        return service.create(request).map(ResponseEntity::ok);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<ProductResponse>> update(@RequestBody @Valid UpdateProductRequest request,
                                                        @PathVariable("id") Long id) {
        log.info("Updating product {}", request);
        return service.update(request, id).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductResponse>> get(@PathVariable("id") Long id) {
        log.info("Getting product {}", id);
        return service.get(id).map(ResponseEntity::ok);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Flux<ProductResponse>> getCategoryProducts(@PathVariable("id") Long categoryId) {
        log.info("[Getting category products] categoryId: {}", categoryId);
        return ResponseEntity.ok(service.getCategoryProducts(categoryId));
    }

}
