package com.github.son_daehyeon.domain.auth.util;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.github.son_daehyeon.common.api.ApiException;
import com.github.son_daehyeon.common.property.WinkProperty;
import com.github.son_daehyeon.domain.user.schema.User;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestInstance;
import kong.unirest.core.json.JSONObject;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WinkApi {

	private final WinkProperty winkProperty;

	public User fromToken(String token) {

		try (UnirestInstance instance = Unirest.spawnInstance()) {

			HttpResponse<JsonNode> response = instance.post("https://wink.kookmin.ac.kr/api/application/oauth/token")
				.header("Content-Type", "application/json")
				.body(Map.ofEntries(
					Map.entry("clientId", winkProperty.getClientId()),
					Map.entry("clientSecret", winkProperty.getClientSecret()),
					Map.entry("token", token)
				))
				.asJson();

			if (!response.isSuccess()) throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus() + " " + response.getStatusText());

			JSONObject body = response.getBody().getObject();

			boolean success = body.getBoolean("success");
			Object error = body.get("error");
			if (!success) throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, error.toString());

			JSONObject user = body.getJSONObject("content").getJSONObject("user");

			return User.builder()
				.id(user.getString("id"))
				.name(user.getString("name"))
				.avatar(user.get("avatar") instanceof String ? user.getString("avatar") : null)
				.build();
		}
	}
}
