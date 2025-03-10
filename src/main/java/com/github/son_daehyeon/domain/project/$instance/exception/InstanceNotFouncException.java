package com.github.son_daehyeon.domain.project.$instance.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class InstanceNotFouncException extends ApiException {

    public InstanceNotFouncException() {

        super(HttpStatus.NOT_FOUND, "인스턴스를 찾을 수 없습니다.");
    }
}
