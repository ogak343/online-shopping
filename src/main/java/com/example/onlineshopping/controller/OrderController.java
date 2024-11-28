package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.CreateOrderRequest;
import com.example.onlineshopping.dto.request.UpdateOrderRequest;
import com.example.onlineshopping.dto.response.OrderResponse;
import com.example.onlineshopping.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {

        log.info("Create order request: {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @PatchMapping
    public ResponseEntity<OrderResponse> update(@Valid @RequestBody UpdateOrderRequest request) {

        log.info("Update order request: {}", request);
        return ResponseEntity.ok(service.update(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable("id") Long id) {

        log.info("Get order: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getAll() {

        log.info("Get all orders: ");
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> delete(@PathVariable Long id) {
        log.info("Delete order request: {}", id);
        return ResponseEntity.ok(service.cancel(id));
    }
}

