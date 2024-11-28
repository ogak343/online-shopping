package com.example.onlineshopping.controller;

import com.example.onlineshopping.dto.request.CreatePaymentRequest;
import com.example.onlineshopping.dto.request.PerformPaymentRequest;
import com.example.onlineshopping.dto.response.PaymentResponse;
import com.example.onlineshopping.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/init")
    public ResponseEntity<PaymentResponse> init(@RequestBody @Valid CreatePaymentRequest request) {

        log.info("Init payment request: {}", request);
        return ResponseEntity.ok(service.init(request));
    }

    @PostMapping("/perform")
    public ResponseEntity<PaymentResponse> perform(@Valid @RequestBody PerformPaymentRequest request) {
        log.info("Perform payment request: {}", request);
        return ResponseEntity.ok(service.perform(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable("id") UUID id) {

        log.info("Get payment by id: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        log.info("Get all payments");
        return ResponseEntity.ok(service.getAll());
    }

}
