package com.common.lib.authModule.authDto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * DTO used for login requests.
 * Contains email and password.
 *
 * @author Ihor Murashko
 */
public record LoginCredentialsDto(
        @NonNull
        @NotBlank
        String email,

        @NonNull
        @NotBlank
        String password) implements Serializable {
}

