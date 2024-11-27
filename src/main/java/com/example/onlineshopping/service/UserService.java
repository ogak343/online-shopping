package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.UserConfirmRequest;
import com.example.onlineshopping.dto.request.UserCreateRequest;
import com.example.onlineshopping.dto.request.UserUpdateRequest;
import com.example.onlineshopping.dto.response.UserCreateResponse;
import com.example.onlineshopping.dto.response.UserResponse;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.mapper.UserMapper;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.OffsetDateTime;

@Service
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final OtpService otpService;
    private final NotificationService notificationService;

    public UserService(UserRepository repository,
                       UserMapper mapper,
                       OtpService otpService,
                       NotificationService notificationService
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.otpService = otpService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Mono<UserCreateResponse> create(UserCreateRequest request) {
        return repository.existsByUsername(request.username())
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) return Mono.error(new CustomException(ErrorCode.USER_EXISTS));
                    return repository.save(mapper.toEntity(request))
                            .flatMap(entity -> otpService.create(entity.getId())
                                    .flatMap(otp -> notificationService.sendOtp(otp.getCode(), entity.getEmail())
                                            .then(Mono.defer(() -> getSuccessMessage(entity.getEmail())
                                                    .map(message -> new UserCreateResponse(otp.getId(), message))))
                                    )
                            );
                });
    }


    public Mono<UserResponse> update(UserUpdateRequest request) {
        return AppUtils.userId()
                .flatMap(userId -> repository.findById(userId)
                        .switchIfEmpty(Mono.error(new CustomException(ErrorCode.USER_NOT_FOUND))))
                .flatMap(user -> {
                    mapper.update(user, request);
                    return repository.save(user).map(mapper::toResponse);
                });
    }

    @Transactional
    public Mono<Void> confirm(UserConfirmRequest request) {
        return otpService.get(request.otpId())
                .publishOn(Schedulers.boundedElastic())
                .flatMap(otp -> {
                    if (otp.getExpiredAt().isBefore(OffsetDateTime.now()))
                        return Mono.error(new CustomException(ErrorCode.INVALID_OTP));
                    if (!otp.getCode().equals(request.otpCode()))
                        return Mono.error(new CustomException(ErrorCode.INVALID_OTP_CODE));
                    return repository.findById(otp.getUserId())
                            .switchIfEmpty(Mono.error(new CustomException(ErrorCode.USER_NOT_FOUND)))
                            .publishOn(Schedulers.boundedElastic())
                            .flatMap(user -> {
                                otp.setConfirmed(true);
                                user.setVerified(true);
                                return Mono.zip(
                                        repository.save(user),
                                        otpService.save(otp)
                                ).then();
                            });
                });
    }

    public Mono<UserResponse> profile() {
        return AppUtils.userId()
                .flatMap(userId -> repository.findById(userId)
                        .switchIfEmpty(Mono.error(new CustomException(ErrorCode.USER_NOT_FOUND)))
                        .map(mapper::toResponse));
    }

    private Mono<String> getSuccessMessage(String email) {
        return Mono.just(String.format("We have sent OTP code to %s", email));
    }
}
