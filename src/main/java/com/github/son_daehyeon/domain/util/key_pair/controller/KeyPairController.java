package com.github.son_daehyeon.domain.util.key_pair.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.son_daehyeon.common.api.dto.response.ApiResponse;
import com.github.son_daehyeon.common.docs.swagger.SwaggerConfig;
import com.github.son_daehyeon.domain.util.key_pair.dto.response.KeyPairResponse;
import com.github.son_daehyeon.domain.util.key_pair.service.KeyPairService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/util/key-pair")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "[Util] Key Pair")
@SecurityRequirement(name = SwaggerConfig.SWAGGER_AUTH)
public class KeyPairController {

    private final KeyPairService keyPairService;

    @PostMapping()
    @Operation(summary = "Key Pair 발급")
    public ApiResponse<KeyPairResponse> generate() {

        return ApiResponse.ok(keyPairService.generate());
    }
}
