package com.github.son_daehyeon.domain.project.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class AlreadyProjectParticipantException extends ApiException {

    public AlreadyProjectParticipantException() {

        super(HttpStatus.BAD_REQUEST, "이미 프로젝트에 참가중입니다.");
    }
}
