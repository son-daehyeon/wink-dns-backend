package com.github.son_daehyeon.domain.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.son_daehyeon.common.api.dto.response.ApiResponse;
import com.github.son_daehyeon.domain.auth.dto.request.LoginRequest;
import com.github.son_daehyeon.domain.auth.dto.request.RefreshTokenRequest;
import com.github.son_daehyeon.domain.auth.dto.response.LoginResponse;
import com.github.son_daehyeon.domain.auth.service.AuthService;
import com.github.son_daehyeon.domain.user.dto.response.UserResponse;
import com.github.son_daehyeon.domain.user.schema.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "[Auth] Index")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {

        return ApiResponse.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "토큰 갱신")
    public ApiResponse<LoginResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {

        return ApiResponse.ok(authService.refreshToken(request));
    }

    @GetMapping("/me")
    @Operation(summary = "내 정보 확인")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<UserResponse> me(@AuthenticationPrincipal User user) {

        return ApiResponse.ok(authService.me(user));
    }
}
