package com.github.son_daehyeon.domain.util.key_pair.dto.response;

import lombok.Builder;

@Builder
public record KeyPairResponse(

	String privateKey,
	String publicKey
) {}
