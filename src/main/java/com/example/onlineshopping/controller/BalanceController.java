package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.CreateBalanceRequest;
import com.example.onlineshopping.dto.request.FullFillBalanceRequest;
import com.example.onlineshopping.dto.response.BalanceResponse;
import com.example.onlineshopping.service.BalanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/balances")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class BalanceController {

    private final BalanceService service;

    public BalanceController(BalanceService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_CASHIER')")
    public ResponseEntity<BalanceResponse> createBalance(@RequestBody @Valid CreateBalanceRequest request) {

        log.info("Create balance: {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/full-fill")
    @PreAuthorize(value = "hasAuthority('ROLE_CASHIER')")
    public ResponseEntity<BalanceResponse> fullFillBalance(@RequestBody @Valid FullFillBalanceRequest request) {

        log.info("FullFill balance: {}", request);
        return ResponseEntity.ok(service.fullFill(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BalanceResponse> get(@PathVariable UUID id) {
        log.info("Get balance by id: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BalanceResponse>> getAll() {
        log.info("Get all balances");
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize(value = "hasAuthority('ROLE_CASHIER')")
    public ResponseEntity<List<BalanceResponse>> getUser(@PathVariable Long userId) {
        log.info("Get user balance by userId: {}", userId);
        return ResponseEntity.ok(service.getByUserId(userId));
    }
}
