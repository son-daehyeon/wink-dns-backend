package com.github.son_daehyeon.common.api;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.son_daehyeon.common.api.exception.ApiException;

import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class Api {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final OkHttpClient client = new OkHttpClient.Builder()
		.connectTimeout(60, TimeUnit.SECONDS)
		.readTimeout(60, TimeUnit.SECONDS)
		.writeTimeout(60, TimeUnit.SECONDS)
		.build();

	public Map<String, Object> http(String url, HttpMethod method, Map.Entry<String, String>... bodies) {

		return http(url, method, "", bodies);
	}

	@SneakyThrows(IOException.class)
	@SuppressWarnings("unchecked")
	public Map<String, Object> http(String url, HttpMethod method, String token, Map.Entry<String, String>... bodies) {

		Request.Builder builder = new Request.Builder()
			.url(url)
			.addHeader("Authorization", token);

		if (!method.equals(HttpMethod.GET) && !method.equals(HttpMethod.DELETE)) {

			Map<String, String> bodyMap = Map.ofEntries(bodies);
			String jsonBody = objectMapper.writeValueAsString(bodyMap);
			RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
			builder = builder.method(method.name(), body).addHeader("Content-Type", "application/json");
		} else {
			builder = builder.method(method.name(), null);
		}

		try (Response response = client.newCall(builder.build()).execute()) {
			if (!response.isSuccessful()) throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, response.message());

			String responseBody = response.body().string();
			if (Objects.isNull(response.body().contentType())) return Map.of();
			return (Map<String, Object>)objectMapper.readValue(responseBody, Map.class);
		}
	}
}
