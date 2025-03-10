package com.github.son_daehyeon.domain.project.$instance.$status.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class InstanceIsNotRunningException extends ApiException {

    public InstanceIsNotRunningException() {

        super(HttpStatus.BAD_REQUEST, "인스턴스가 실행중인 상태가 아닙니다.");
    }
}
