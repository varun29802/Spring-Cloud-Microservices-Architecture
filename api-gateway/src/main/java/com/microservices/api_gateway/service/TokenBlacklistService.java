package com.microservices.api_gateway.service;

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class TokenBlacklistService {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public TokenBlacklistService(ReactiveRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<Boolean> blacklistToken(String token, long ttlInSeconds) {
        return redisTemplate.opsForValue().set(token, "blacklisted", Duration.ofSeconds(ttlInSeconds));
    }

    public Mono<Boolean> isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(token);
    }
}