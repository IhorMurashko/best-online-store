package com.bestStore.core.constants;

/**
 * Defines standard HTTP header names used for authentication and user context propagation.
 *
 * <p>
 * Includes both public-facing headers like {@code Authorization} and internal headers such as
 * {@code X-Internal-Secret}, which may be used between microservices.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
public class HeadersConstants {

    private HeadersConstants() {
    }

    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String HEADER_USERNAME = "X-Username";
    public static final String HEADER_ROLES = "X-Roles";
    public static final String HEADER_TOKEN_TYPE = "X-Token-Type";

    public static final String HEADER_AUTHENTICATION = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String HEADER_REQUEST_TOKEN_ID = "X-Request-Token-Id";
    public static final String HEADER_INTERNAL_SECRET = "X-Internal-Secret";


}
