package com.bestStore.gateway.adapter;

import com.bestStore.core.claims.ClaimsProvider;
import com.bestStore.core.constants.HeadersConstants;
import com.bestStore.core.constants.TokenClaimsConstants;
import com.bestStore.core.exceptions.ExceptionMessageConstants;
import com.bestStore.core.exceptions.claimsException.JwtClaimsException;
import com.bestStore.core.headerHandling.HeaderAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Adapter that converts a JWT token string into a structured {@link HttpHeaders} object
 * by extracting specific claims and placing them into predefined header fields.
 *
 * <p>
 * This class is typically used in the API Gateway to extract user context (ID, roles, username, token type)
 * from the token and forward them as headers to downstream microservices.
 * </p>
 *
 * <p>
 * The extracted claims include:
 * <ul>
 *     <li>User ID ({@code X-User-Id})</li>
 *     <li>Username ({@code X-Username})</li>
 *     <li>Roles ({@code X-Roles})</li>
 *     <li>Token type ({@code X-Token-Type})</li>
 * </ul>
 * </p>
 *
 * <p>
 * Throws {@link JwtClaimsException} if any required claim is missing or malformed.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
public class HttpHeaderAdapter implements HeaderAdapter<HttpHeaders, String> {

    private final ClaimsProvider claimsProvider;
    private final ObjectMapper objectMapper;


    public HttpHeaderAdapter(ClaimsProvider claimsProvider) {
        this.claimsProvider = claimsProvider;
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public HttpHeaders convert(@NonNull String token) {

        String userId = extractRequiredClaim(token, TokenClaimsConstants.USER_ID_CLAIM, String.class);
        String username = extractRequiredClaim(token, TokenClaimsConstants.USERNAME_CLAIM, String.class);
        String tokenType = extractRequiredClaim(token, TokenClaimsConstants.TOKEN_TYPE_CLAIM, String.class);


        Set<String> rolesList = Optional.of(claimsProvider.extractClaimFromToken(token, claims -> {
                    Object raw = claims.get(TokenClaimsConstants.ROLES_CLAIM);

                    if (raw instanceof String roleString) {
                        if (roleString.contains(",")) {
                            return Arrays.stream(roleString.split(","))
                                    .map(String::trim)
                                    .collect(Collectors.toSet());
                        } else {
                            return Set.of(roleString);
                        }
                    } else if (raw instanceof Collection<?> rawCollection) {
                        Set<String> roles = rawCollection.stream()
                                .filter(Objects::nonNull)
                                .map(roleObj -> {
                                    if (roleObj instanceof Map<?, ?> roleMap) {
                                        Object authority = roleMap.get("authority");
                                        if (authority instanceof String str) {
                                            return str;
                                        }
                                    }
                                    throw new IllegalArgumentException("Unexpected role object: " + roleObj);
                                })
                                .collect(Collectors.toSet());
                        return roles;
                    } else {
                        throw new IllegalArgumentException("Unexpected type for roles claim: " + raw.getClass());
                    }
                }))
                .orElseThrow(() -> new JwtClaimsException(
                        String.format(ExceptionMessageConstants.TOKEN_DOESNT_HAVE_REQUIRED_CLAIM, "roles")
                ));

        String role = String.join(",", rolesList);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HeadersConstants.HEADER_USER_ID, userId);
        headers.set(HeadersConstants.HEADER_USERNAME, username);
        headers.set(HeadersConstants.HEADER_TOKEN_TYPE, tokenType);
        headers.set(HeadersConstants.HEADER_ROLES, role);
        return headers;
    }


    private <T> T extractRequiredClaim(String token, String claimKey, Class<T> clazz) {
        return Optional.ofNullable(claimsProvider.extractClaimFromToken(token,
                        claims -> claims.get(claimKey, clazz)))
                .orElseThrow(() -> new JwtClaimsException(
                        String.format(ExceptionMessageConstants.TOKEN_DOESNT_HAVE_REQUIRED_CLAIM, claimKey)
                ));
    }
}
