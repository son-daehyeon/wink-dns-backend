package com.github.son_daehyeon.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.ApiException;

public class AccessTokenExpiredException extends ApiException {

    public AccessTokenExpiredException() {

        super(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다.");
    }
}
