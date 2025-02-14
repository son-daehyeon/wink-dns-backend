package com.github.son_daehyeon.domain.auth.service;

import org.springframework.stereotype.Service;

import com.github.son_daehyeon.common.security.jwt.JwtUtil;
import com.github.son_daehyeon.domain.auth.dto.request.LoginRequest;
import com.github.son_daehyeon.domain.auth.dto.request.RefreshTokenRequest;
import com.github.son_daehyeon.domain.auth.dto.response.LoginResponse;
import com.github.son_daehyeon.domain.auth.exception.AuthenticationFailException;
import com.github.son_daehyeon.domain.auth.exception.FeeNotPaidException;
import com.github.son_daehyeon.domain.auth.exception.InvalidRefreshTokenException;
import com.github.son_daehyeon.domain.auth.repository.RefreshTokenRepository;
import com.github.son_daehyeon.domain.auth.schema.RefreshToken;
import com.github.son_daehyeon.domain.auth.util.WinkOauth;
import com.github.son_daehyeon.domain.user.dto.response.UserResponse;
import com.github.son_daehyeon.domain.user.repository.UserRepository;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtil jwtUtil;
    private final WinkOauth winkOauth;

    public LoginResponse login(LoginRequest dto) {

        User user = userRepository.save(winkOauth.fromToken(dto.token()));
        if (!user.isFee()) throw new FeeNotPaidException();

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return LoginResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public LoginResponse refreshToken(RefreshTokenRequest dto) {

        RefreshToken token = refreshTokenRepository.findByToken(dto.token()).orElseThrow(InvalidRefreshTokenException::new);
        refreshTokenRepository.delete(token);

        User user = userRepository.findById(token.userId()).orElseThrow(AuthenticationFailException::new);

        String accessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        return LoginResponse.builder()
            .accessToken(accessToken)
            .refreshToken(newRefreshToken)
            .build();
    }

    public UserResponse me(User user) {

        return UserResponse.builder()
            .user(user)
            .build();
    }
}
