package com.github.son_daehyeon.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.wink")
public class WinkProperty {

	@NotBlank
	private String clientId;

	@NotBlank
	private String clientSecret;
}
