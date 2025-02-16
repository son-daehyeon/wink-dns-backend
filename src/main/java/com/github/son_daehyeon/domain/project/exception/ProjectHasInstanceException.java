package com.github.son_daehyeon.domain.project.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.github.son_daehyeon.common.api.exception.ApiException;
import com.github.son_daehyeon.domain.project.__sub__.instance.schema.Instance;

public class ProjectHasInstanceException extends ApiException {

    public ProjectHasInstanceException(List<Instance> instances) {

        super(HttpStatus.BAD_REQUEST, "프로젝트에 있는 %d개의 인스턴스를 먼저 삭제해주세요.".formatted(instances.size()));
    }
}
