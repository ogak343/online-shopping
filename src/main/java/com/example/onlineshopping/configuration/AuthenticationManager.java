package com.example.onlineshopping.configuration;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.contants.Role;
import com.example.onlineshopping.exception.CustomException;
import com.example.onlineshopping.repository.UserRepository;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthenticationManager(JwtTokenProvider jwtTokenProvider,
                                 UserRepository userRepository
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty((String) authentication.getCredentials())
                .filter(header -> header != null && header.startsWith("Bearer "))
                .map(header -> header.substring(7))
                .flatMap(jwtTokenProvider::getUserIdFromToken)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCode.INVALID_TOKEN)))
                .flatMap(userRepository::findById)
                .map(user -> new UsernamePasswordAuthenticationToken(user.getUsername(), null, getAuthorities(user.getRole())));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singleton(authority);
    }
}
