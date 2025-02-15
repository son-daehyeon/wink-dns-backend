package com.github.son_daehyeon.domain.instance.dto.response;

import com.github.son_daehyeon.domain.instance.schema.Instance;

import lombok.Builder;

@Builder
public record InstanceResponse(

	Instance instance
) {
}
