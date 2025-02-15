package com.github.son_daehyeon.domain.instance.util;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.github.son_daehyeon.common.api.Api;
import com.github.son_daehyeon.common.property.ProxmoxProperty;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProxmoxApi extends Api {

	private final ProxmoxProperty proxmoxProperty;

	@SuppressWarnings({"unchecked", "BusyWait"})
	@SafeVarargs
	@Override
	public final Map<String, Object> http(String url, HttpMethod method, Map.Entry<String, String>... bodies) {

		Map<String, Object> response = super.http(generateUrl(url), method, generateToken(), bodies);

		if (!Objects.isNull(response.get("data")) && response.get("data") instanceof String upid && upid.startsWith("UPID")) {
			return CompletableFuture
				.supplyAsync(() -> {
					Map<String, Object> statusResponse;

					do {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							throw new RuntimeException("Status check interrupted", e);
						}

						statusResponse = super.http(
							generateUrl("/tasks/%s/status".formatted(upid)),
							HttpMethod.GET,
							generateToken()
						);
					} while (!((Map<String, Object>) statusResponse.get("data")).get("status").equals("stopped"));

					return response;
				})
				.join();
		}

		return (Map<String, Object>) response.get("data");
	}

	private String generateUrl(String sub) {

		return proxmoxProperty.getHost() + "/api2/json/nodes/" + proxmoxProperty.getNode() + sub;
	}

	private String generateToken() {

		return "PVEAPIToken=%s!%s=%s".formatted(proxmoxProperty.getUser(), proxmoxProperty.getName(), proxmoxProperty.getToken());
	}
}
