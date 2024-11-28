package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.UserConfirmRequest;
import com.example.onlineshopping.dto.request.UserCreateRequest;
import com.example.onlineshopping.dto.request.UserUpdateRequest;
import com.example.onlineshopping.dto.response.UserCreateResponse;
import com.example.onlineshopping.dto.response.UserResponse;
import com.example.onlineshopping.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserCreateResponse> create(@RequestBody @Valid UserCreateRequest request) {

        log.info("Creating user {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(@RequestBody @Valid UserConfirmRequest request) {

        log.info("Confirming user {}", request);
        service.confirm(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest request) {
        log.info("Updating user {}", request);
        return ResponseEntity.ok(service.update(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> get() {

        log.info("Retrieving user profile");
        return ResponseEntity.ok(service.profile());
    }
}
