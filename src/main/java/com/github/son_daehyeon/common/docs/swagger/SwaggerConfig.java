package com.github.son_daehyeon.common.docs.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {

		return new OpenAPI()
			.components(new Components().addSecuritySchemes("JWT", securityScheme()))
			.info(info());
	}

	private Info info() {

		return new Info()
			.title("Title")
			.description("Application Description")
			.version("1.0");
	}

	private SecurityScheme securityScheme() {

		return new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.bearerFormat("JWT")
			.scheme("bearer");
	}
}