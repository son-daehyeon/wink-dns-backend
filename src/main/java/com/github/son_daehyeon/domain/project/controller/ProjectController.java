package com.github.son_daehyeon.domain.project.controller;

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
import com.github.son_daehyeon.domain.project.dto.request.CreateProjectRequest;
import com.github.son_daehyeon.domain.project.dto.response.ProjectResponse;
import com.github.son_daehyeon.domain.project.dto.response.ProjectsResponse;
import com.github.son_daehyeon.domain.project.service.ProjectService;
import com.github.son_daehyeon.domain.user.schema.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "[Project] Index")
@SecurityRequirement(name = SwaggerConfig.SWAGGER_AUTH)
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "내 프로젝트 목록")
    public ApiResponse<ProjectsResponse> myProjects(@AuthenticationPrincipal User user) {

        return ApiResponse.ok(projectService.myProjects(user));
    }

    @GetMapping("/invited")
    @Operation(summary = "초대된 프로젝트 목록")
    public ApiResponse<ProjectsResponse> invitedProjects(@AuthenticationPrincipal User user) {

        return ApiResponse.ok(projectService.invitedProjects(user));
    }

    @PostMapping
    @Operation(summary = "프로젝트 생성")
    public ApiResponse<ProjectResponse> createProject(@RequestBody @Valid CreateProjectRequest request, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(projectService.createProject(request, user));
    }

    @PostMapping("/{projectId}/invite/{userId}")
    @Operation(summary = "프로젝트 초대")
    public ApiResponse<ProjectResponse> inviteUser(@PathVariable String projectId, @PathVariable String userId, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(projectService.inviteUser(projectId, userId, user));
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "프로젝트 수정")
    public ApiResponse<ProjectResponse> updateProject(@PathVariable String projectId, @RequestBody @Valid CreateProjectRequest request, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(projectService.updateProject(projectId, request, user));
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "프로젝트 삭제")
    public ApiResponse<Void> deleteProject(@PathVariable String projectId, @AuthenticationPrincipal User user) {

        projectService.deleteProject(projectId, user);

        return ApiResponse.ok();
    }
}
