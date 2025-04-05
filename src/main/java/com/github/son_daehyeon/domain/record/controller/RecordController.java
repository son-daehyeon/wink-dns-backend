package com.github.son_daehyeon.domain.record.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.son_daehyeon.common.api.ApiController;
import com.github.son_daehyeon.common.api.ApiResponse;
import com.github.son_daehyeon.common.api.guard.AuthGuard;
import com.github.son_daehyeon.domain.record.dto.request.CreateRecordRequest;
import com.github.son_daehyeon.domain.record.dto.request.UpdateRecordRequest;
import com.github.son_daehyeon.domain.record.dto.response.GetRecordResponse;
import com.github.son_daehyeon.domain.record.dto.response.GetRecordsResponse;
import com.github.son_daehyeon.domain.record.service.RecordService;
import com.github.son_daehyeon.domain.user.schema.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@AuthGuard
@ApiController("/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ApiResponse<GetRecordsResponse> getRecords(@AuthenticationPrincipal User user) {

        return ApiResponse.ok(recordService.getRecords(user));
    }

    @PostMapping
    public ApiResponse<GetRecordResponse> createRecord(@RequestBody @Valid CreateRecordRequest request, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(recordService.createRecord(request, user));
    }

    @PutMapping("/{recordId}")
    public ApiResponse<GetRecordResponse> updateRecord(@PathVariable String recordId, @RequestBody @Valid UpdateRecordRequest request, @AuthenticationPrincipal User user) {

        return ApiResponse.ok(recordService.updateRecord(recordId, request, user));
    }

    @DeleteMapping("/{recordId}")
    public ApiResponse<Void> deleteRecord(@PathVariable String recordId, @AuthenticationPrincipal User user) {

        recordService.deleteRecord(recordId, user);

        return ApiResponse.ok();
    }
}
