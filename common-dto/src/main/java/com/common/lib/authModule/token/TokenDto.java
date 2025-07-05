package com.common.lib.authModule.token;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

public record TokenDto(
        @NonNull
        @NotBlank
        String accessToken,
        @NonNull
        @NotBlank
        String refreshToken) {
}
