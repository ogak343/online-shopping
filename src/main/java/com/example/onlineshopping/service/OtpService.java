package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.UserConfirmRequest;
import com.example.onlineshopping.entity.Otp;
import com.example.onlineshopping.exception.CustomerException;
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

    public Mono<Otp> save(Long userId) {
        long code = randomGenerator.nextInt(100000, 999999);
        Otp otp = new Otp();
        otp.setUserId(userId);
        otp.setCode(code);
        otp.setConfirmed(false);
        otp.setCreatedAt(OffsetDateTime.now());
        otp.setExpiredAt(OffsetDateTime.now().plusMinutes(otpDuration));
        return repository.save(otp);
    }

    public void confirm(UserConfirmRequest request) {
        repository.findById(request.otpId())
                .switchIfEmpty(Mono.error(new CustomerException(ErrorCode.OTP_FAILED)))
                .flatMap(entity -> {
                    if (!entity.getCode().equals(request.otpCode())) {
                        return Mono.error(new CustomerException(ErrorCode.OTP_FAILED));
                    }
                    entity.setConfirmed(true);
                    return repository.save(entity);
                })
                .subscribe();
    }
}
