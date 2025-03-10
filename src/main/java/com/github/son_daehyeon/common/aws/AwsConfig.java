package com.github.son_daehyeon.common.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.son_daehyeon.common.property.AwsProperty;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.route53.Route53Client;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

	private final AwsProperty awsProperty;

	@Bean
	public AwsBasicCredentials awsBasicCredentials() {

		return AwsBasicCredentials.create(awsProperty.getAccessKey(), awsProperty.getSecretKey());
	}

	@Bean
	public Route53Client route53Client() {

		return Route53Client.builder()
			.region(Region.of(awsProperty.getRegion()))
			.credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials()))
			.build();
	}
}
