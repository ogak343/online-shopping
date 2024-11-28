package com.example.onlineshopping.service;

import com.example.onlineshopping.configuration.JwtTokenProvider;
import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.dto.request.LoginReq;
import com.example.onlineshopping.dto.response.LoginResp;
import com.example.onlineshopping.entity.User;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.repository.UserRepository;
import com.example.onlineshopping.utils.AppUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final String REFRESH_TOKEN = "refresh";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(UserRepository userRepository,
                                 BCryptPasswordEncoder encoder,
                                 JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResp login(LoginReq request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getVerified()) throw new CustomException(ErrorCode.USER_UNVERIFIED);
        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_CREDENTIALS);
        }
        return jwtTokenProvider.generateToken(user);
    }

    public LoginResp refresh() {
        String token = AppUtils.getHeader(REFRESH_TOKEN)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));
        User user = userRepository.findById(AppUtils.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (!jwtTokenProvider.validateToken(token, user.getId())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        return jwtTokenProvider.generateToken(user);
    }
}
