package com.github.son_daehyeon.domain.project.$instance.dto.request;

import com.github.son_daehyeon.common.validation.custom.Enum;
import com.github.son_daehyeon.domain.project.$instance.constant.OsType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateInstanceRequest(

	@NotBlank
	String name,

	@NotBlank
	String publicKey,

	@NotBlank
	@Enum(enumClass = OsType.class)
	String osType,

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
