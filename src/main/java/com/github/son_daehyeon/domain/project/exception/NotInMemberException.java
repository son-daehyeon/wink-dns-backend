package com.github.son_daehyeon.domain.project.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class NotInMemberException extends ApiException {

    public NotInMemberException() {

        super(HttpStatus.BAD_REQUEST, "프로젝트에 속한 유저가 아닙니다.");
    }
}
