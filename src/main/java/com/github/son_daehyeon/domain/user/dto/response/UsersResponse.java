package com.github.son_daehyeon.domain.user.dto.response;

import java.util.List;

import com.github.son_daehyeon.domain.user.schema.User;

import lombok.Builder;

@Builder
public record UsersResponse(

	List<User> users
) {
}
