package com.bestStore.core.configuration;

import com.bestStore.core.properties.SecurityKeysProperties;
import com.bestStore.core.jwtProvider.DefaultJwtTokenProvider;
import com.bestStore.core.jwtProvider.JwtTokenProvider;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@EnableConfigurationProperties(SecurityKeysProperties.class)
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
}