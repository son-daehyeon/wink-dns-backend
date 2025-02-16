package com.github.son_daehyeon.domain.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.son_daehyeon.common.api.dto.response.ApiResponse;
import com.github.son_daehyeon.domain.user.dto.response.UsersResponse;
import com.github.son_daehyeon.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "[User] Index")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "모든 유저 조회")
    public ApiResponse<UsersResponse> all() {

        return ApiResponse.ok(userService.all());
    }
}
