package com.github.son_daehyeon.domain.auth.util;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.github.son_daehyeon.common.api.Api;
import com.github.son_daehyeon.common.api.exception.ApiException;
import com.github.son_daehyeon.common.property.WinkProperty;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WinkApi {

	private final WinkProperty winkProperty;

	private final Api api;

	@SuppressWarnings({"unchecked"})
	public User fromToken(String token) {

		Map<String, Object> body = api.http(
			"https://wink.daehyeon.cloud/api/application/oauth/token",
			HttpMethod.POST,
			Map.entry("clientId", winkProperty.getClientId()),
			Map.entry("clientSecret", winkProperty.getClientSecret()),
			Map.entry("token", token)
		);

		int statusCode = (int) body.get("statusCode");
		String error = (String) body.get("error");
		if (!Objects.isNull(error)) throw new ApiException(HttpStatus.valueOf(statusCode), error);

		Map<String, Object> user = (Map<String, Object>) ((Map<String, Object>) body.get("content")).get("user");

		return User.builder()
			.id((String) user.get("id"))
			.name((String) user.get("name"))
			.fee((boolean) user.get("fee"))
			.build();
	}
}
