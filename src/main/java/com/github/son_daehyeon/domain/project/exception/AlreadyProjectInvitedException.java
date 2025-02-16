package com.github.son_daehyeon.domain.project.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class AlreadyProjectInvitedException extends ApiException {

    public AlreadyProjectInvitedException() {

        super(HttpStatus.BAD_REQUEST, "이미 프로젝트에 초대했습니다.");
    }
}
