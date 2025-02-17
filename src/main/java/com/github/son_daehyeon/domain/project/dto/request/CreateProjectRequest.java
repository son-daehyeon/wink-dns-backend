package com.github.son_daehyeon.domain.project.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest(

	@NotBlank
	String icon,

	@NotBlank
	String name
) {
}
