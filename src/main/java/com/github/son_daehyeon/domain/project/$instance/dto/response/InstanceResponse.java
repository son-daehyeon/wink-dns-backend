package com.github.son_daehyeon.domain.project.$instance.dto.response;

import com.github.son_daehyeon.domain.project.$instance.schema.Instance;

import lombok.Builder;

@Builder
public record InstanceResponse(

	Instance instance
) {
}
