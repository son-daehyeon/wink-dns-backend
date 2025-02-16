package com.github.son_daehyeon.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException() {

        super(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.");
    }
}
