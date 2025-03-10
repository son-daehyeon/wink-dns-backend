package com.github.son_daehyeon.domain.project.$instance.$matrix.dto.response;

import java.util.List;

import com.github.son_daehyeon.domain.project.$instance.$matrix.dto.internal.InstanceMatrix;

import lombok.Builder;

@Builder
public record InstanceMatrixResponse(

	List<InstanceMatrix> matrix
) {
}
