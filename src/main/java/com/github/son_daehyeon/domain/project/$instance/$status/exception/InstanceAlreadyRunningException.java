package com.github.son_daehyeon.domain.project.$instance.$status.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class InstanceAlreadyRunningException extends ApiException {

    public InstanceAlreadyRunningException() {

        super(HttpStatus.BAD_REQUEST, "인스턴스가 실행중입니다.");
    }
}
