//package com.example.onlineshopping.configuration;
//
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtAuthenticationFilter extends AuthenticationWebFilter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public JwtAuthenticationFilter(ReactiveAuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
//        super(authenticationManager);
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        return Mono.just(exchange.getRequest().getHeaders().getFirst("Authorization"))
//                .flatMap(header-> {
//                    if (header == null || !header.startsWith("Bearer ")) {
//                        return chain.filter(exchange);
//                    }
//                    jwtTokenProvider.getUsernameFromToken(header.substring(7));
//                    return Mono.empty();
//                });
//
//    }
//}
