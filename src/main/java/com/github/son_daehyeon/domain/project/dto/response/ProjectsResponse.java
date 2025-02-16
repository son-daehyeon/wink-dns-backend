package com.github.son_daehyeon.domain.project.dto.response;

import java.util.List;

import com.github.son_daehyeon.domain.project.schema.Project;

import lombok.Builder;

@Builder
public record ProjectsResponse(

	List<Project> projects
) {
}
