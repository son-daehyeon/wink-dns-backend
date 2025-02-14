package com.github.son_daehyeon.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class FeeNotPaidException extends ApiException {

    public FeeNotPaidException() {

        super(HttpStatus.PAYMENT_REQUIRED, "회비 납부자만 이용가능한 서비스입니다.");
    }
}
