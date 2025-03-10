package com.github.son_daehyeon.domain.project.$instance.$status.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.son_daehyeon.common.api.dto.response.ApiResponse;
import com.github.son_daehyeon.common.docs.swagger.SwaggerConfig;
import com.github.son_daehyeon.domain.project.$instance.$status.dto.response.InstanceStatusResponse;
import com.github.son_daehyeon.domain.project.$instance.$status.service.InstanceStatusService;
import com.github.son_daehyeon.domain.user.schema.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/project/{projectId}/instance/{instanceId}/status")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "[Project] [Instance] Status")
@SecurityRequirement(name = SwaggerConfig.SWAGGER_AUTH)
public class InstanceStatusController {

    private final InstanceStatusService instanceStatusService;

    @GetMapping()
    @Operation(summary = "인스턴스 현재 상태")
    public ApiResponse<InstanceStatusResponse> currentStatus(@PathVariable String projectId, @PathVariable String instanceId, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(instanceStatusService.currentState(projectId, instanceId, user));
    }

    @PostMapping("/start")
    @Operation(summary = "인스턴스 시작")
    public ApiResponse<Void> start(@PathVariable String projectId, @PathVariable String instanceId, @AuthenticationPrincipal User user) {

        instanceStatusService.start(projectId, instanceId, user);

        return ApiResponse.ok();
    }

    @PostMapping("/shutdown")
    @Operation(summary = "인스턴스 종료")
    public ApiResponse<Void> shutdown(@PathVariable String projectId, @PathVariable String instanceId, @AuthenticationPrincipal User user) {

        instanceStatusService.shutdown(projectId, instanceId, user);

        return ApiResponse.ok();
    }

    @PostMapping("/stop")
    @Operation(summary = "인스턴스 강제 종료")
    public ApiResponse<Void> stop(@PathVariable String projectId, @PathVariable String instanceId, @AuthenticationPrincipal User user) {

        instanceStatusService.stop(projectId, instanceId, user);

        return ApiResponse.ok();
    }
}
