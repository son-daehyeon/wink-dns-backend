package com.github.son_daehyeon.domain.instance.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.son_daehyeon.common.api.dto.response.ApiResponse;
import com.github.son_daehyeon.domain.instance.dto.response.InstanceStatusResponse;
import com.github.son_daehyeon.domain.instance.service.InstanceStatusService;
import com.github.son_daehyeon.domain.user.schema.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/instance/{id}/status")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "인스턴스 상태")
public class InstanceStatusController {

    private final InstanceStatusService instanceStatusService;

    @GetMapping()
    @Operation(summary = "인스턴스 현재 상태")
    public ApiResponse<InstanceStatusResponse> currentStatus(@PathVariable String id, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(instanceStatusService.currentState(id, user));
    }

    @PostMapping("/start")
    @Operation(summary = "인스턴스 시작")
    public ApiResponse<Void> start(@PathVariable String id, @AuthenticationPrincipal User user) {

        instanceStatusService.start(id, user);

        return ApiResponse.ok();
    }

    @PostMapping("/shutdown")
    @Operation(summary = "인스턴스 종료")
    public ApiResponse<Void> shutdown(@PathVariable String id, @AuthenticationPrincipal User user) {

        instanceStatusService.shutdown(id, user);

        return ApiResponse.ok();
    }

    @PostMapping("/stop")
    @Operation(summary = "인스턴스 강제 종료")
    public ApiResponse<Void> stop(@PathVariable String id, @AuthenticationPrincipal User user) {

        instanceStatusService.stop(id, user);

        return ApiResponse.ok();
    }

    @PostMapping("/restart")
    @Operation(summary = "인스턴스 재시작")
    public ApiResponse<Void> restart(@PathVariable String id, @AuthenticationPrincipal User user) {

        instanceStatusService.restart(id, user);

        return ApiResponse.ok();
    }
}
