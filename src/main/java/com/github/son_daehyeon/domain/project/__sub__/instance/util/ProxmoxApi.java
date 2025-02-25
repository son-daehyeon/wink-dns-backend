package com.github.son_daehyeon.domain.project.__sub__.instance.util;

import java.net.http.HttpConnectTimeoutException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.github.son_daehyeon.common.api.exception.ApiException;
import com.github.son_daehyeon.common.property.ProxmoxProperty;

import kong.unirest.core.ContentType;
import kong.unirest.core.HttpRequestWithBody;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;
import kong.unirest.core.UnirestInstance;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProxmoxApi {

	private final ProxmoxProperty proxmoxProperty;

	@SafeVarargs
	public final Optional<JsonNode> http(String url, HttpMethod method, Map.Entry<String, String>... bodies) {

		try (UnirestInstance instance = Unirest.spawnInstance()) {

			HttpRequestWithBody request = instance.request(method.name(), generateUrl(url))
				.header("Authorization", generateToken());
			HttpResponse<JsonNode> response =
				method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT) || method.equals(HttpMethod.PATCH)
					? request.contentType(ContentType.APPLICATION_JSON).body(Map.ofEntries(bodies)).asJson()
					: request.asJson();

			if (!response.isSuccess())
				throw new ApiException(HttpStatus.valueOf(response.getStatus()),
					response.getBody().getObject().optString("message", "클라우드와 연결하던 중 오류가 발생했습니다."));

			Object data = response.getBody().getObject().get("data");

			if (data instanceof String upid && upid.startsWith("UPID")) {

				return CompletableFuture
					.supplyAsync(() -> {
						HttpResponse<JsonNode> response1;

						do {
							try {
								//noinspection BusyWait
								Thread.sleep(200);
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
								throw new RuntimeException("Status check interrupted", e);
							}

							response1 = instance.request("GET", generateUrl("/tasks/%s/status".formatted(upid)))
								.header("Authorization", generateToken())
								.asJson();
						} while (!response1.getBody()
							.getObject()
							.getJSONObject("data")
							.getString("status")
							.equals("stopped"));

						return Optional.of(response.getBody());
					})
					.join();
			}

			return Objects.isNull(data) ? Optional.empty() : Optional.of(new JsonNode(data.toString()));
		} catch (UnirestException e) {
			if (e.getCause().getClass().equals(HttpConnectTimeoutException.class)) {
				throw new ApiException(HttpStatus.BAD_GATEWAY, "호스트 서버와 연결할 수 없습니다");
			} else {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}
		}
	}

	private String generateUrl(String sub) {

		return proxmoxProperty.getHost() + "/api2/json/nodes/" + proxmoxProperty.getNode() + sub;
	}

	private String generateToken() {

		return "PVEAPIToken=%s!%s=%s".formatted(proxmoxProperty.getUser(), proxmoxProperty.getName(), proxmoxProperty.getToken());
	}
}
