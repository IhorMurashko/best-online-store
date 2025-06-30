package com.bestStore.gateway.revokedTokenService.tokenCache;

import com.bestStore.core.utils.TokenRedisKeyUtil;
import com.bestStore.gateway.claims.ClaimsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;
/**
 * Redis-based implementation of {@link RevokedTokenCache}.
 *
 * <p>
 * Stores revoked tokens with a TTL derived from the token's expiration time.
 * Uses Base64-encoded Redis keys prefixed with {@code revoked:} to isolate entries.
 * </p>
 *
 * <p>
 * Handles silent failures during token parsing to avoid disruption of the main flow.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
public class RedisRevokeToken implements RevokedTokenCache {

    private final Logger log = LoggerFactory.getLogger(RedisRevokeToken.class);
    private final StringRedisTemplate redisTemplate;
    private final ClaimsProvider claimsProvider;

    public RedisRevokeToken(StringRedisTemplate redisTemplate, ClaimsProvider claimsProvider) {
        this.redisTemplate = redisTemplate;
        this.claimsProvider = claimsProvider;
    }

    @Override
    public void saveToken(@NonNull String... tokens) {
        for (String t : tokens) {

            try {

                long accessTokenExpiration = claimsProvider.extractClaimFromToken(t, claims ->
                        claims.getExpiration().getTime());

                long accessTokenTTL = accessTokenExpiration - System.currentTimeMillis();

                if (accessTokenTTL > 0) {
                    String redisKey = "revoked:" + TokenRedisKeyUtil.generateBase64RedisKey(t);
                    redisTemplate.opsForValue().set(redisKey, "revoked tokens", accessTokenTTL, TimeUnit.MILLISECONDS);
                    log.info("Revoked token {}, with TTL: {} ms", redisKey, accessTokenTTL);
                }
            } catch (Exception e) {
                log.warn("Failed to revoke tokens: {}", t, e);
            }


        }
    }

    @Override
    public boolean isTokenPresent(@NonNull String token) {
        String tokenKey = "revoked:" + TokenRedisKeyUtil.generateBase64RedisKey(token);
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenKey));
    }


}

