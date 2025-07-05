package com.bestStore.core.configuration;

import com.bestStore.core.claims.ClaimsProvider;
import com.bestStore.core.claims.DefaultClaimsProvider;
import com.bestStore.core.jwtProvider.DefaultJwtTokenProvider;
import com.bestStore.core.jwtProvider.JwtTokenProvider;
import com.bestStore.core.properties.SecurityKeysProperties;
import com.bestStore.core.properties.TokenBlackListProperties;
import com.bestStore.core.revokedTokenService.DelegationRevokeTokenService;
import com.bestStore.core.revokedTokenService.RevokeTokenService;
import com.bestStore.core.revokedTokenService.tokenCache.RedisRevokeToken;
import com.bestStore.core.revokedTokenService.tokenCache.RevokedTokenCache;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * Core auto-configuration for the security starter module.
 * <p>
 * Registers default implementations for {@link SecretKey} and {@link JwtTokenProvider}
 * if no custom beans are defined by the consuming application.
 * </p>
 *
 * <p>
 * Reads the secret key for JWT signing from {@link SecurityKeysProperties} and exposes it
 * as a Spring bean. This secret is then used by the default {@link DefaultJwtTokenProvider}.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
@Configuration
@EnableConfigurationProperties({SecurityKeysProperties.class, TokenBlackListProperties.class})
public class SecurityCoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SecretKey.class)
    public SecretKey jwtSecretKey(SecurityKeysProperties securityKeysProperties) {
        String jwtSecret = securityKeysProperties.jwt();
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    @ConditionalOnMissingBean(JwtTokenProvider.class)
    public JwtTokenProvider defaultJwtTokenProvider(SecretKey secretKey) {
        return new DefaultJwtTokenProvider(secretKey);
    }

    @Bean
    @ConditionalOnMissingBean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnProperty(prefix = "security-lib.black-list",
            name = "enabled",
            havingValue = "true")
    @ConditionalOnMissingBean(DelegationRevokeTokenService.class)
    public RevokeTokenService delegationRevokeTokenService(RevokedTokenCache revokedTokenCache) {
        return new DelegationRevokeTokenService(revokedTokenCache);
    }

    @Bean
    @ConditionalOnProperty(prefix = "security-lib.black-list",
            name = "enabled",
            havingValue = "true")
    @ConditionalOnMissingBean(RevokedTokenCache.class)
    @ConditionalOnClass(StringRedisTemplate.class)
    public RevokedTokenCache redisRevokeToken(StringRedisTemplate redisTemplate, ClaimsProvider claimsProvider) {
        return new RedisRevokeToken(redisTemplate, claimsProvider);
    }

    @Bean
    @ConditionalOnMissingBean(ClaimsProvider.class)
    public ClaimsProvider defaultClaimsProvider(SecretKey jwtKey) {
        return new DefaultClaimsProvider(jwtKey);
    }

}