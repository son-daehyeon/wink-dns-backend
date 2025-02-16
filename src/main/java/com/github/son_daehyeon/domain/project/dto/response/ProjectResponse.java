package com.github.son_daehyeon.domain.project.dto.response;

import com.github.son_daehyeon.domain.project.schema.Project;

import lombok.Builder;

@Builder
public record ProjectResponse(

	Project project
) {
}
