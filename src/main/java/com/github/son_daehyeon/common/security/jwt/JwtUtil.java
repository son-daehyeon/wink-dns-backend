package com.github.son_daehyeon.common.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.github.son_daehyeon.common.property.JwtProperty;
import com.github.son_daehyeon.domain.auth.repository.RefreshTokenRepository;
import com.github.son_daehyeon.domain.auth.schema.RefreshToken;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperty jwtProperty;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public Algorithm algorithm() {

        return Algorithm.HMAC256(jwtProperty.getKey());
    }

    public String generateAccessToken(User user) {

        return JWT.create()
            .withIssuedAt(Instant.now())
            .withExpiresAt(Instant.now().plus(jwtProperty.getAccessTokenExpirationHours(), ChronoUnit.HOURS))
            .withClaim("id", user.getId())
            .sign(algorithm());
    }

    public String generateRefreshToken(User user) {

        String token = JWT.create()
            .withIssuedAt(Instant.now())
            .withExpiresAt(Instant.now().plus(jwtProperty.getRefreshTokenExpirationHours(), ChronoUnit.HOURS))
            .sign(algorithm());

        RefreshToken refreshToken = RefreshToken.builder()
            .userId(user.getId())
            .token(token)
            .ttl(jwtProperty.getRefreshTokenExpirationHours())
            .build();

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public String extractToken(String token) {

        return JWT.require(algorithm())
            .build()
            .verify(token)
            .getClaim("id")
            .asString();
    }

    public boolean validateToken(String token) throws TokenExpiredException {

        if (token == null) return false;

        try {
            JWT.require(algorithm()).build().verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }
}
