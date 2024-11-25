package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.UserConfirmRequest;
import com.example.onlineshopping.dto.request.UserCreateRequest;
import com.example.onlineshopping.dto.request.UserUpdateRequest;
import com.example.onlineshopping.dto.response.UserCreateResponse;
import com.example.onlineshopping.dto.response.UserResponse;
import com.example.onlineshopping.entity.User;
import com.example.onlineshopping.exception.CustomerException;
import com.example.onlineshopping.mapper.UserMapper;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.utils.AppUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
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
        boolean exists = repository.existsByUsername(request.username());
        if (exists) throw new CustomerException(ErrorCode.USER_EXISTS);

        return repository.save(mapper.toEntity(request))
                .flatMap(entity ->
                        otpService.save(entity.getId())
                                .flatMap(otp ->
                                        notificationService.sendOtp(Mono.just(otp.getCode()), entity.getEmail())
                                                .thenReturn(new UserCreateResponse(
                                                        otp.getId(),
                                                        getSuccessMessage(entity.getEmail())
                                                ))
                                )
                );
    }

    @Transactional
    public Mono<UserResponse> update(UserUpdateRequest request) {
        Long userId = AppUtils.userId();
        Mono<User> user = repository.findById(userId)
                .switchIfEmpty(Mono.error(new CustomerException(ErrorCode.USER_NOT_FOUND)));

        return user.flatMap(entity -> {
            mapper.update(entity, request);
            return repository.save(entity).map(mapper::toResponse);
        });
    }

    @Transactional
    public Mono<Void> confirm(UserConfirmRequest request) {
        otpService.confirm(request);

        return null;
    }

    public Mono<UserResponse> profile() {
        Long userId = AppUtils.userId();
        return repository.findById(userId).map(mapper::toResponse);
    }

    private String getSuccessMessage(String email) {
        return String.format("We have sent OTP code to %s", email);
    }
}
