package com.github.son_daehyeon.domain.instance.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.son_daehyeon.common.api.dto.response.ApiResponse;
import com.github.son_daehyeon.common.validation.custom.Enum;
import com.github.son_daehyeon.domain.instance.constant.TimeUnit;
import com.github.son_daehyeon.domain.instance.dto.response.InstanceMatrixResponse;
import com.github.son_daehyeon.domain.instance.service.InstanceMatrixService;
import com.github.son_daehyeon.domain.user.schema.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/instance/{id}/matrix")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "인스턴스 성능")
public class InstanceMatrixController {

    private final InstanceMatrixService instanceMatrixService;

    @GetMapping()
    @Operation(summary = "인스턴스 성능")
    public ApiResponse<InstanceMatrixResponse> getMatrix(
        @PathVariable String id,
        @RequestParam(defaultValue = "HOUR") @Enum(enumClass = TimeUnit.class) String timeUnit,
        @AuthenticationPrincipal User user) {

        return ApiResponse.ok(instanceMatrixService.getMatrix(id, TimeUnit.valueOf(timeUnit), user));
    }
}
