package com.github.son_daehyeon.domain.instance.dto.response;

import java.util.List;

import com.github.son_daehyeon.domain.instance.dto.internal.InstanceMatrix;

import lombok.Builder;

@Builder
public record InstanceMatrixResponse(

	List<InstanceMatrix> matrix
) {
}
