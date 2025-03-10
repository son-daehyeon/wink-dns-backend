package com.github.son_daehyeon.domain.record.controller;

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
import com.github.son_daehyeon.domain.record.dto.request.CreateRecordRequest;
import com.github.son_daehyeon.domain.record.dto.request.UpdateRecordRequest;
import com.github.son_daehyeon.domain.record.dto.response.GetRecordResponse;
import com.github.son_daehyeon.domain.record.dto.response.GetRecordsResponse;
import com.github.son_daehyeon.domain.record.service.RecordService;
import com.github.son_daehyeon.domain.user.schema.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "[Record] Index")
@SecurityRequirement(name = SwaggerConfig.SWAGGER_AUTH)
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    @Operation(summary = "내 레코드 조회")
    public ApiResponse<GetRecordsResponse> getRecords(@AuthenticationPrincipal User user) {

        return ApiResponse.ok(recordService.getRecords(user));
    }

    @PostMapping
    @Operation(summary = "레코드 추가")
    public ApiResponse<GetRecordResponse> createRecord(@RequestBody @Valid CreateRecordRequest request, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(recordService.createRecord(request, user));
    }

    @PutMapping("/{recordId}")
    @Operation(summary = "레코드 수정")
    public ApiResponse<GetRecordResponse> updateRecord(@PathVariable String recordId, @RequestBody @Valid UpdateRecordRequest request, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(recordService.updateRecord(recordId, request, user));
    }

    @DeleteMapping("/{recordId}")
    @Operation(summary = "레코드 삭제")
    public ApiResponse<Void> deleteRecord(@PathVariable String recordId, @AuthenticationPrincipal User user) {

        recordService.deleteRecord(recordId, user);

        return ApiResponse.ok();
    }
}
