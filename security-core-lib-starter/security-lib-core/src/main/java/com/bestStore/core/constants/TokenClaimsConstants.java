package com.bestStore.core.constants;

/**
 * Defines keys used in JWT claims to store user identity and token metadata.
 *
 * <p>
 * These constants ensure consistent claim naming across token generation and validation logic.
 * </p>
 */
public class TokenClaimsConstants {
    private TokenClaimsConstants() {
    }

    // === JWT ===
    public static final String TOKEN_TYPE_CLAIM = "tokenType";

    // === CLAIMS ===
    public static final String USER_ID_CLAIM = "id";
    public static final String USERNAME_CLAIM = "username";
    public static final String ROLES_CLAIM = "roles";
}
