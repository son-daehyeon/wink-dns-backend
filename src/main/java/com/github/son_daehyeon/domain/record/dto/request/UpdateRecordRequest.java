package com.github.son_daehyeon.domain.record.dto.request;

import java.util.List;

import com.github.son_daehyeon.common.validation.custom.Enum;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import software.amazon.awssdk.services.route53.model.RRType;

public record UpdateRecordRequest(

	@NotNull
	@Enum(enumClass = RRType.class)
	String type,

	@Min(60)
	@Max(172_800)
	int ttl,

	@NotNull
	@Size(min = 1, max = 100)
	List<@NotBlank String> records
) {
}
