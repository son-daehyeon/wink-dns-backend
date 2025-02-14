package com.github.son_daehyeon.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshTokenRequest(

    @NotBlank
    String token
) {
}
