package com.bestStore.core.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
/**
 * Utility class for generating Base64-encoded Redis keys from JWT tokens.
 *
 * <p>
 * This is typically used to store or blacklist tokens by their encoded form
 * in Redis or similar key-value storage systems.
 * </p>
 */
public class TokenRedisKeyUtil {

    private TokenRedisKeyUtil() {
    }

    /**
     * Encodes the given token as a Base64 string for use as a Redis key.
     *
     * @param token the JWT token string
     * @return base64-encoded Redis-safe key
     */
    public static String generateBase64RedisKey(String token) {
        return Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
    }

}
