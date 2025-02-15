package com.github.son_daehyeon.domain.instance.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class NotExistsInstanceException extends ApiException {

    public NotExistsInstanceException() {

        super(HttpStatus.NOT_FOUND, "인스턴스를 찾을 수 없습니다.");
    }
}
