package com.github.son_daehyeon.domain.project.$instance.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateInstanceRequest(

	@NotBlank
	String name,

	@Min(1)
	@Max(4)
	int core,

	@Min(512)
	@Max(4096)
	int memory,

	@Min(0)
	@Max(4096)
	int swap,

	@Min(8)
	@Max(128)
	int disk
) {
}
