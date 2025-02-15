package com.github.son_daehyeon.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.proxmox")
public class ProxmoxProperty {

	@NotBlank
	private String host;

	@NotBlank
	private String node;

	@NotBlank
	private String user;

	@NotBlank
	private String name;

	@NotBlank
	private String token;
}
