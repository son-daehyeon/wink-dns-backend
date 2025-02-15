package com.github.son_daehyeon.domain.key.dto.response;

import lombok.Builder;

@Builder
public record KeyResponse(

	String privateKey,
	String publicKey
) {}
