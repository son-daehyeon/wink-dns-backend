package com.github.son_daehyeon.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.aws")
public class AwsProperty {

	@NotBlank
	private String accessKey;

	@NotBlank
	private String secretKey;

	@NotBlank
	private String region;

	@NestedConfigurationProperty
	private Route53Config route53;

	@Data
	public static class Route53Config {

		@NotBlank
		String hostedZoneId;
	}
}
