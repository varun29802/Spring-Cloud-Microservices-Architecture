package com.microservices.api_gateway.controller;

import com.microservices.api_gateway.service.TokenBlacklistService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class MainController {

    private final TokenBlacklistService blacklistService;

    public MainController(TokenBlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    @PostMapping("/auth/logout")
    public Mono<String> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

//        ttlTimeCalculation But not working for current version
//        Jwt jwt = decodeJwt(token);
//        Instant exp = jwt.getExpiresAt();
//        long ttlInSeconds = exp.getEpochSecond() - Instant.now().getEpochSecond();

        int ttlInSeconds = 900;  // 15 min token will store in redis cache

        return blacklistService.blacklistToken(token, ttlInSeconds)
                .thenReturn("Logged out successfully");
    }

    private Jwt decodeJwt(String token) {
        // This bean is available if Spring Security is configured correctly
        return org.springframework.security.oauth2.jwt.JwtDecoders
                .fromIssuerLocation("http://keycloak:8080/realms/Micro-Service")
                .decode(token);
    }
}
