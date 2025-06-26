package com.beststore.rest.context.dto;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
/**
 * Immutable representation of the authenticated user context extracted from request headers.
 *
 * <p>
 * Contains the user ID, username, and granted roles. Used across the application
 * for authorization decisions and audit logging.
 * </p>
 *
 * @param id user identifier
 * @param username user login name
 * @param roles user authorities parsed from {@code X-Roles} header
 *
 * @author Ihor Murashko
 */
public record UserContext(
        @NonNull
        @NotEmpty
        String id,
        @NonNull
        @NotEmpty
        String username,
        @NonNull
        @NotEmpty
        Set<? extends GrantedAuthority> roles
) {
}
