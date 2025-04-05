package com.github.son_daehyeon.domain.auth.service;

import org.springframework.stereotype.Service;

import com.github.son_daehyeon.common.security.jwt.JwtUtil;
import com.github.son_daehyeon.domain.auth.dto.request.LoginRequest;
import com.github.son_daehyeon.domain.auth.dto.response.LoginResponse;
import com.github.son_daehyeon.domain.auth.util.WinkApi;
import com.github.son_daehyeon.domain.user.dto.response.UserResponse;
import com.github.son_daehyeon.domain.user.repository.UserRepository;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;
    private final WinkApi winkApi;

    public LoginResponse login(LoginRequest dto) {

        User user = userRepository.save(winkApi.fromToken(dto.token()));

        return LoginResponse.builder()
            .token(jwtUtil.generateToken(user))
            .build();
    }

    public UserResponse me(User user) {

        return UserResponse.builder()
            .user(user)
            .build();
    }
}
