package com.github.son_daehyeon.domain.instance.controller;

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
import com.github.son_daehyeon.domain.instance.dto.request.CreateInstanceRequest;
import com.github.son_daehyeon.domain.instance.dto.request.UpdateInstanceRequest;
import com.github.son_daehyeon.domain.instance.dto.response.InstanceResponse;
import com.github.son_daehyeon.domain.instance.dto.response.InstancesResponse;
import com.github.son_daehyeon.domain.instance.service.InstanceService;
import com.github.son_daehyeon.domain.user.schema.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/instance")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "인스턴스")
public class InstanceController {

    private final InstanceService instanceService;

    @GetMapping()
    @Operation(summary = "내 인스턴스 목록")
    public ApiResponse<InstancesResponse> myInstances(@AuthenticationPrincipal User user) {

        return ApiResponse.ok(instanceService.myInstances(user));
    }

    @PostMapping
    @Operation(summary = "인스턴스 생성")
    public ApiResponse<InstanceResponse> createInstance(@RequestBody @Valid CreateInstanceRequest body, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(instanceService.createInstance(body, user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "인스턴스 수정")
    public ApiResponse<InstanceResponse> updateInstance(@PathVariable String id, @RequestBody @Valid UpdateInstanceRequest body, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(instanceService.updateInstance(id, body, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "인스턴스 삭제")
    public ApiResponse<Void> deleteInstance(@PathVariable String id, @AuthenticationPrincipal User user) {

        instanceService.deleteInstance(id, user);

        return ApiResponse.ok();
    }
}
