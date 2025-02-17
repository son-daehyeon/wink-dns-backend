package com.github.son_daehyeon.domain.project.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class CannotDeleteMyselfException extends ApiException {

    public CannotDeleteMyselfException() {

        super(HttpStatus.BAD_REQUEST, "나 자신은 삭제할 수 없습니다.");
    }
}
