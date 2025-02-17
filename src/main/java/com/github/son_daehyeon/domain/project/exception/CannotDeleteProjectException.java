package com.github.son_daehyeon.domain.project.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class CannotDeleteProjectException extends ApiException {

    public CannotDeleteProjectException() {

        super(HttpStatus.BAD_REQUEST, "최소 1개의 프로젝트가 존재하여야 합니다.");
    }
}
