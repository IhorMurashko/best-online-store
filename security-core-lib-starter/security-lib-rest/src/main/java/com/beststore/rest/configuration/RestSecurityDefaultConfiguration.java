package com.beststore.rest.configuration;

import com.bestStore.core.headerHandling.HeaderAdapter;
import com.beststore.rest.adapter.UserContextHeaderAdapter;
import com.bestStore.core.properties.SecurityKeysProperties;
import com.beststore.rest.accessHandling.RestAccessDeniedHandler;
import com.beststore.rest.accessHandling.RestAuthEntryPoint;
import com.beststore.rest.context.dto.UserContext;
import com.beststore.rest.filter.DefaultOncePerRequestHttpFilter;
import com.beststore.rest.properties.RestTokenModeProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.OncePerRequestFilter;
/**
 * Default bean configuration for REST-based security components.
 *
 * <p>
 * Registers fallback implementations for:
 * <ul>
 *     <li>{@link AuthenticationEntryPoint} — handles unauthenticated requests</li>
 *     <li>{@link AccessDeniedHandler} — handles forbidden requests</li>
 *     <li>{@link OncePerRequestFilter} — injects {@link UserContext} into request scope</li>
 *     <li>{@link HeaderAdapter} — parses headers into {@link UserContext}</li>
 * </ul>
 * </p>
 *
 * <p>
 * All beans are conditionally registered to allow for user overrides.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
@EnableConfigurationProperties(RestTokenModeProperties.class)
@Configuration
public class RestSecurityDefaultConfiguration {
    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthEntryPoint();
    }


    @Bean
    @ConditionalOnMissingBean(AccessDeniedHandler.class)
    public AccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(OncePerRequestFilter.class)
    public OncePerRequestFilter defaultOnePerRequestHttpFilter(SecurityKeysProperties securityKeysProperties,
                                                               HeaderAdapter<UserContext, HttpServletRequest> userContextHeaderAdapter,
                                                               RestTokenModeProperties restTokenModeProperties) {
        return new DefaultOncePerRequestHttpFilter(securityKeysProperties, userContextHeaderAdapter, restTokenModeProperties);
    }

    @Bean
    @ConditionalOnMissingBean(HeaderAdapter.class)
    public HeaderAdapter<UserContext, HttpServletRequest> userContextHeaderAdapter() {
        return new UserContextHeaderAdapter();
    }
}
