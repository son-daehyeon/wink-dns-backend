package com.github.son_daehyeon.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequest(

    @NotBlank
    @Email
    String email,

    @NotBlank
    String password
) {
}