package com.github.son_daehyeon.domain.instance.dto.response;

import lombok.Builder;

@Builder
public record InstanceStatusResponse(

	boolean running,
	int uptime
) {
}
