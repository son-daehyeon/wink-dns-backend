package com.github.son_daehyeon.domain.project.$instance.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class CannotShrinkDiskException extends ApiException {

    public CannotShrinkDiskException() {

        super(HttpStatus.BAD_REQUEST, "디스크 용량을 줄일 수 없습니다.");
    }
}
