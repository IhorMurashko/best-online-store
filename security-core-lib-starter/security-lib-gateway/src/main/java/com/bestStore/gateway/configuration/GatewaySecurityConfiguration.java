package com.bestStore.gateway.configuration;

import com.bestStore.core.headerHandling.HeaderAdapter;
import com.bestStore.core.jwtProvider.JwtTokenProvider;
import com.bestStore.core.properties.SecurityKeysProperties;
import com.bestStore.gateway.adapter.HttpHeaderAdapter;
import com.bestStore.core.claims.ClaimsProvider;
import com.bestStore.gateway.filter.GatewayAuthenticationFilter;
import com.bestStore.core.revokedTokenService.RevokeTokenService;
import com.bestStore.core.revokedTokenService.tokenCache.RevokedTokenCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * Auto-configuration class for enabling gateway-specific security features.
 *
 * <p>
 * Registers beans used to process incoming requests in API Gateway:
 * <ul>
 *     <li>{@link GatewayAuthenticationFilter} — authentication filter for validating JWT and enriching headers</li>
 *     <li>{@link HttpHeaderAdapter} — adapter to extract claims and build {@link HttpHeaders}</li>
 *     <li>{@link RevokeTokenService} — service to check if token is revoked</li>
 *     <li>{@link RevokedTokenCache} — cache implementation (e.g. Redis) for revoked tokens</li>
 *     <li>{@link ClaimsProvider} — parser for JWT claims</li>
 * </ul>
 * </p>
 *
 * <p>
 * Beans are only registered if missing, allowing overrides in consuming applications.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
@Configuration
public class GatewaySecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean(GatewayAuthenticationFilter.class)
    public GlobalFilter gatewayAuthenticationFilter(SecurityKeysProperties securityKeysProperties,
                                                    JwtTokenProvider jwtTokenProvider,
                                                    HeaderAdapter<HttpHeaders, String> headerAdapter,
                                                    RevokeTokenService delegationRevokeTokenService) {
        return new GatewayAuthenticationFilter(securityKeysProperties, jwtTokenProvider,
                headerAdapter, delegationRevokeTokenService);
    }


    @Bean
    @ConditionalOnMissingBean(HttpHeaderAdapter.class)
    public HeaderAdapter<HttpHeaders, String> httpHeaderAdapter(ClaimsProvider claimsProvider) {
        return new HttpHeaderAdapter(claimsProvider);
    }

//    @Bean
//    @ConditionalOnMissingBean(DelegationRevokeTokenService.class)
//    public RevokeTokenService delegationRevokeTokenService(RevokedTokenCache revokedTokenCache) {
//        return new DelegationRevokeTokenService(revokedTokenCache);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(RevokedTokenCache.class)
//    @ConditionalOnClass(StringRedisTemplate.class)
//    public RevokedTokenCache redisRevokeToken(StringRedisTemplate redisTemplate, ClaimsProvider claimsProvider) {
//        return new RedisRevokeToken(redisTemplate, claimsProvider);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(ClaimsProvider.class)
//    public ClaimsProvider defaultClaimsProvider(SecretKey jwtKey) {
//        return new DefaultClaimsProvider(jwtKey);
//    }

}
