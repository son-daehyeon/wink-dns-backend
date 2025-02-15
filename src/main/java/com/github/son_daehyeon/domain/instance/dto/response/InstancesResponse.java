package com.github.son_daehyeon.domain.instance.dto.response;

import java.util.List;

import com.github.son_daehyeon.domain.instance.schema.Instance;

import lombok.Builder;

@Builder
public record InstancesResponse(

	List<Instance> instances
) {
}
