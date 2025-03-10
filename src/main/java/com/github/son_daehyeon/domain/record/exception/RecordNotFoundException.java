package com.github.son_daehyeon.domain.record.exception;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;

public class RecordNotFoundException extends ApiException {

	public RecordNotFoundException() {

		super(HttpStatus.NOT_FOUND, "DNS를 찾을 수 없습니다.");
	}
}
