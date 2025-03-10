package com.github.son_daehyeon.domain.project.$instance.$status.dto.response;

import lombok.Builder;

@Builder
public record InstanceStatusResponse(

	boolean running,
	int uptime
) {
}
