package com.github.son_daehyeon.domain.project.__sub__.instance.dto.response;

import com.github.son_daehyeon.domain.project.__sub__.instance.schema.Instance;

import lombok.Builder;

@Builder
public record InstanceResponse(

	Instance instance
) {
}
