package com.github.son_daehyeon.domain.record.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.ApiException;

public class DuplicatedRecordNameException extends ApiException {

	public DuplicatedRecordNameException(String name) {

		super(HttpStatus.CONFLICT, "이미 %s 도메인이 사용중입니다.".formatted(name));
	}
}
