package com.github.son_daehyeon.domain.project.__sub__.instance.__sub__.status.dto.response;

import lombok.Builder;

@Builder
public record InstanceStatusResponse(

	boolean running,
	int uptime
) {
}
