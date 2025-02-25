package com.github.son_daehyeon.domain.project.__sub__.instance.__sub__.matrix.dto.response;

import com.github.son_daehyeon.domain.project.__sub__.instance.__sub__.matrix.dto.internal.InstanceMatrix;

import lombok.Builder;

@Builder
public record InstanceCurrentMatrixResponse(

	InstanceMatrix matrix
) {
}
