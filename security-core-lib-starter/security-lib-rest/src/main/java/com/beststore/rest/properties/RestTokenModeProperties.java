package com.beststore.rest.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * Configuration properties related to refresh token handling in REST microservices.
 *
 * <p>
 * Controlled by prefix {@code security-lib.refresh-token.mode}.
 * Enables optional header-based handling of refresh-access token routing.
 * </p>
 *
 * @param enabled whether refresh token mode is active
 * @param allowedRefreshAccess list of allowed endpoints for access token use during refresh
 */

/**
 * * Configuration properties related to refresh token handling in REST microservices.
 * *
 * * <p>
 * * Controlled by prefix {@code security-lib.refresh-token.mode}.
 * * Enables optional header-based handling of refresh-access token routing.
 * * </p>
 * *
 * * @param enabled whether refresh token mode is active
 * * @param allowedRefreshAccess list of allowed endpoints for access token use during refresh
 */
@ConfigurationProperties(prefix = "security-lib.refresh-token.mode")
public record RestTokenModeProperties(
        boolean enabled,
        String[] allowedRefreshAccess

) {
}
