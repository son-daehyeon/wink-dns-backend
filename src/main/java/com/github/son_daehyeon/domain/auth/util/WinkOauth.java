package com.github.son_daehyeon.domain.auth.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.github.son_daehyeon.common.api.exception.ApiException;
import com.github.son_daehyeon.common.property.WinkProperty;
import com.github.son_daehyeon.domain.user.schema.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WinkOauth {

	private final WinkProperty winkProperty;

	private final RestTemplate restTemplate;

	@SuppressWarnings({"unchecked", "rawtypes"})
	public User fromToken(String token) {

		String url = "https://wink.daehyeon.cloud/api/application/oauth/token";

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("clientId", winkProperty.getClientId());
		requestBody.put("clientSecret", winkProperty.getClientSecret());
		requestBody.put("token", token);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
		Map<String, Object> body = (Map<String, Object>)Objects.requireNonNull(response.getBody());

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
