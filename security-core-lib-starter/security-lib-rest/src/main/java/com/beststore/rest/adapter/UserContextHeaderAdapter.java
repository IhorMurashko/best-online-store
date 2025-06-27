package com.beststore.rest.adapter;

import com.bestStore.core.constants.HeadersConstants;
import com.bestStore.core.exceptions.ExceptionMessageConstants;
import com.bestStore.core.exceptions.claimsException.HeaderClaimException;
import com.bestStore.core.headerHandling.HeaderAdapter;
import com.beststore.rest.context.dto.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adapter that converts an {@link HttpServletRequest} into a {@link UserContext}
 * by extracting user-related headers injected by the API Gateway.
 *
 * <p>
 * Expects the following headers to be present:
 * <ul>
 *     <li>{@code X-User-Id}</li>
 *     <li>{@code X-Username}</li>
 *     <li>{@code X-Roles} (comma-separated)</li>
 * </ul>
 * </p>
 *
 * <p>
 * If any of these headers are missing or invalid, a {@link HeaderClaimException} is thrown.
 * </p>
 *
 * <p>
 * This adapter is used in REST controllers and security layers to construct an internal
 * {@link UserContext} object for authorization and auditing.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
public class UserContextHeaderAdapter implements HeaderAdapter<UserContext, HttpServletRequest> {


    @Override
    public UserContext convert(@NonNull HttpServletRequest request) {


        String userId = getRequiredHeader(request, HeadersConstants.HEADER_USER_ID);
        String username = getRequiredHeader(request, HeadersConstants.HEADER_USERNAME);
        String roles = getRequiredHeader(request, HeadersConstants.HEADER_ROLES);

        if (roles.isBlank()) {
            throw new HeaderClaimException(
                    String.format(
                            ExceptionMessageConstants
                                    .HEADER_DOESNT_HAVE_REQUIRED_RAW, "roles"
                    )
            );
        }

        Set<SimpleGrantedAuthority> userRoles = Arrays.stream(roles.split(","))
                .map(String::trim)
                .filter(r -> !r.isBlank())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Set::copyOf));

        return new UserContext(userId, username, userRoles);
    }


    private String getRequiredHeader(HttpServletRequest request, String headerName) {
        return Optional.ofNullable(request.getHeader(headerName))
                .orElseThrow(() ->
                        new HeaderClaimException(
                                String.format(
                                        ExceptionMessageConstants
                                                .HEADER_DOESNT_HAVE_REQUIRED_RAW, headerName)
                        ));
    }
}
