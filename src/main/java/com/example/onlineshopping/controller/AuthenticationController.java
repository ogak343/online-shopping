package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.LoginReq;
import com.example.onlineshopping.dto.response.LoginResp;
import com.example.onlineshopping.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResp> login(@RequestBody @Valid LoginReq dto) {

        log.info("LoginReq: {}", dto);
        return ResponseEntity.ok(service.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResp> refresh() {

        log.info("Refresh");
        return ResponseEntity.ok(service.refresh());
    }
}
