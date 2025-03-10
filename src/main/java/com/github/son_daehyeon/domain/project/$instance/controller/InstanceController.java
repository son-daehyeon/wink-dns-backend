package com.github.son_daehyeon.domain.project.$instance.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.son_daehyeon.common.api.dto.response.ApiResponse;
import com.github.son_daehyeon.common.docs.swagger.SwaggerConfig;
import com.github.son_daehyeon.domain.project.$instance.dto.request.CreateInstanceRequest;
import com.github.son_daehyeon.domain.project.$instance.dto.request.UpdateInstanceRequest;
import com.github.son_daehyeon.domain.project.$instance.dto.response.InstanceResponse;
import com.github.son_daehyeon.domain.project.$instance.dto.response.InstancesResponse;
import com.github.son_daehyeon.domain.project.$instance.service.InstanceService;
import com.github.son_daehyeon.domain.user.schema.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/project/{projectId}/instance")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "[Project] [Instance] Index")
@SecurityRequirement(name = SwaggerConfig.SWAGGER_AUTH)
public class InstanceController {

    private final InstanceService instanceService;

    @GetMapping()
    @Operation(summary = "내 인스턴스 목록")
    public ApiResponse<InstancesResponse> myInstances(@PathVariable String projectId, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(instanceService.myInstances(projectId, user));
    }

    @PostMapping
    @Operation(summary = "인스턴스 생성")
    public ApiResponse<InstanceResponse> createInstance(@PathVariable String projectId, @RequestBody @Valid CreateInstanceRequest body, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(instanceService.createInstance(projectId, body, user));
    }

    @PutMapping("/{instanceId}")
    @Operation(summary = "인스턴스 수정")
    public ApiResponse<InstanceResponse> updateInstance(@PathVariable String projectId, @PathVariable String instanceId, @RequestBody @Valid UpdateInstanceRequest body, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(instanceService.updateInstance(projectId, instanceId, body, user));
    }

    @DeleteMapping("/{instanceId}")
    @Operation(summary = "인스턴스 삭제")
    public ApiResponse<Void> deleteInstance(@PathVariable String projectId, @PathVariable String instanceId, @AuthenticationPrincipal User user) {

        instanceService.deleteInstance(projectId, instanceId, user);

        return ApiResponse.ok();
    }
}
