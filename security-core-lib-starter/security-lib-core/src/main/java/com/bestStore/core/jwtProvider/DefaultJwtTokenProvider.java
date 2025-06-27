package com.bestStore.core.jwtProvider;

import com.bestStore.core.constants.TokenClaimsConstants;
import com.bestStore.core.constants.TokenType;
import com.bestStore.core.utils.DateConstructorUtil;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
/**
 * Default implementation of {@link JwtTokenProvider} based on JJWT library.
 *
 * <p>
 * Uses HMAC SHA-256 signing with a shared secret key. Supports token generation with
 * subject, claims, type and configurable expiration.
 * </p>
 *
 * <p>
 * Token validation includes signature verification and expiration check.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
public class DefaultJwtTokenProvider implements JwtTokenProvider {

    private final Logger log = LoggerFactory.getLogger(DefaultJwtTokenProvider.class);

    private final SecretKey key;

    public DefaultJwtTokenProvider(SecretKey key) {
        this.key = key;
    }


    @Override
    public String generateToken(@NonNull String subject,
                                @NonNull Long validityPeriodInSeconds,
                                @NonNull Map<String, Object> claims,
                                @NonNull TokenType tokenType) {

        Date now = new Date();
        Date expiryDate = DateConstructorUtil.dateExpirationGenerator(now, validityPeriodInSeconds);


        return
                Jwts.builder()
                        .subject(subject)
                        .issuedAt(now)
                        .expiration(expiryDate)
                        .claims(claims)
                        .claim(TokenClaimsConstants.TOKEN_TYPE_CLAIM, tokenType)
                        .signWith(key)
                        .compact();

    }

    @Override
    public boolean validateToken(@NonNull String token) {

        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            log.warn("Invalid token: {}", ex.getMessage());
            return false;
        }
    }
}
