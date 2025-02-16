package com.github.son_daehyeon.domain.project.dto.request;

import java.util.List;

public record InviteProjectRequest(

	List<String> users
) {
}
