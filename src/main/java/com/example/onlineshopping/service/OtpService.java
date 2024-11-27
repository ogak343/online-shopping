package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.entity.Otp;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.OffsetDateTime;

@Service
public class OtpService {

    private final OtpRepository repository;

    @Value("${otp.duration}")
    private Long otpDuration;

    private final SecureRandom randomGenerator;

    public OtpService(OtpRepository repository,
                      SecureRandom randomGenerator
    ) {
        this.repository = repository;
        this.randomGenerator = randomGenerator;
    }

    public Mono<Otp> create(Long userId) {
        long code = randomGenerator.nextInt(100000, 999999);
        Otp otp = new Otp();
        otp.setUserId(userId);
        otp.setCode(code);
        otp.setConfirmed(false);
        otp.setCreatedAt(OffsetDateTime.now());
        otp.setExpiredAt(OffsetDateTime.now().plusMinutes(otpDuration));
        return repository.save(otp);
    }

    public Mono<Otp> save(Otp otp) {
        return repository.save(otp);
    }

    public Mono<Otp> get(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.OTP_NOT_FOUND)));
    }
}
