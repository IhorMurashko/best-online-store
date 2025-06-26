package com.bestStore.core.properties;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
/**
 * Configuration properties that hold the internal and JWT secret keys used
 * by the security library.
 *
 * <p>
 * These keys are injected from the application's configuration using the prefix
 * {@code security-lib.secret.key}. Both keys must be between 32 and 256 characters.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 *
 * @param internal internal shared secret (e.g., for internal service authentication)
 * @param jwt      secret used to sign JWT tokens
 */
@ConfigurationProperties(prefix = "security-lib.secret.key")
@Validated
public record SecurityKeysProperties(


        @NotNull
        @Size(min = 32, max = 256, message = "Secret key must be between 32 and 256 characters long")
        String internal,


        @NotNull
        @Size(min = 32, max = 256, message = "Secret key must be between 32 and 256 characters long")
        String jwt) {


}
