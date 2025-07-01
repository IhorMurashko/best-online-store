package com.bestStore.gateway.claims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.function.Function;
/**
 * Default implementation of {@link ClaimsProvider} using JJWT.
 *
 * <p>
 * Parses a JWT token using a shared secret key and applies the provided function
 * to extract specific claims from the token's payload.
 * </p>
 *
 * <p>
 * Logs detailed error information if token is malformed.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
public class DefaultClaimsProvider implements ClaimsProvider {

    private final Logger log = LoggerFactory.getLogger(DefaultClaimsProvider.class);

    private final SecretKey key;

    public DefaultClaimsProvider(SecretKey key) {
        this.key = key;
    }


    @Override
    public <T> T extractClaimFromToken(String token, Function<Claims, T> function) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key).build()
                    .parseSignedClaims(token)
                    .getPayload();
            return function.apply(claims);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token {}", ex.getMessage());
            throw ex;
        }
    }
}
