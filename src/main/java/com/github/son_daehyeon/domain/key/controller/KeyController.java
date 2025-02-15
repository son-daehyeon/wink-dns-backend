package com.github.son_daehyeon.domain.key.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.son_daehyeon.common.api.dto.response.ApiResponse;
import com.github.son_daehyeon.domain.key.dto.response.KeyResponse;
import com.github.son_daehyeon.domain.key.service.KeyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/key")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "SSH Key Pair")
public class KeyController {

    private final KeyService keyService;

    @PostMapping()
    @Operation(summary = "Key Pair 발급")
    public ApiResponse<KeyResponse> generate() {

        return ApiResponse.ok(keyService.generate());
    }
}
