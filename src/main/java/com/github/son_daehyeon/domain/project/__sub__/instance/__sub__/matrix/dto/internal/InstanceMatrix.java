package com.github.son_daehyeon.domain.project.__sub__.instance.__sub__.matrix.dto.internal;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record InstanceMatrix(

	LocalDateTime time,

	long maxCpu,
	long currentCpu,

	long maxMemory,
	long currentMemory,

	long maxSwap,
	long currentSwap,

	long maxDisk,
	long currentDisk,

	long diskInput,
	long diskOutput,

	long networkInput,
	long networkOutput
) {
}
