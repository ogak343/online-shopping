package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.UserConfirmRequest;
import com.example.onlineshopping.dto.request.UserCreateRequest;
import com.example.onlineshopping.dto.request.UserUpdateRequest;
import com.example.onlineshopping.dto.response.UserCreateResponse;
import com.example.onlineshopping.dto.response.UserResponse;
import com.example.onlineshopping.entity.Otp;
import com.example.onlineshopping.entity.User;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.mapper.UserMapper;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final OtpService otpService;
    private final NotificationService notificationService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       UserMapper mapper,
                       OtpService otpService,
                       NotificationService notificationService,
                       BCryptPasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.otpService = otpService;
        this.notificationService = notificationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserCreateResponse create(UserCreateRequest request) {
        boolean exists = repository.existsByUsername(request.username());
        if (exists) throw new CustomException(ErrorCode.USER_EXISTS);
        User user = mapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user = repository.save(user);
        Otp otp = otpService.create(user.getId());
        notificationService.sendOtp(otp.getCode(), user.getEmail());
        return new UserCreateResponse(otp.getId(), getSuccessMessage(user.getEmail()));
    }


    public UserResponse update(UserUpdateRequest request) {
        Long userId = AppUtils.userId();
        User entity = repository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        mapper.update(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public void confirm(UserConfirmRequest request) {
        Otp otp = otpService.get(request.otpId());
        if (otp.getExpiredAt().isBefore(OffsetDateTime.now())) throw new CustomException(ErrorCode.INVALID_OTP);
        if (!otp.getCode().equals(request.otpCode())) throw new CustomException(ErrorCode.INVALID_OTP_CODE);
        User user = repository.findById(otp.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        otp.setConfirmed(true);
        user.setVerified(true);
        repository.save(user);
        otpService.save(otp);
    }

    public UserResponse profile() {
        return mapper.toResponse(repository.findById(AppUtils.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
    }

    private String getSuccessMessage(String email) {
        return String.format("We have sent OTP code to %s", email);
    }
}
