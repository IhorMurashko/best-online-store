package com.bestStore.gateway.filter;

import com.bestStore.core.constants.HeadersConstants;
import com.bestStore.core.exceptions.ExceptionMessageConstants;
import com.bestStore.core.headerHandling.HeaderAdapter;
import com.bestStore.core.jwtProvider.JwtTokenProvider;
import com.bestStore.core.properties.SecurityKeysProperties;
import com.bestStore.core.utils.TokenRedisKeyUtil;
import com.bestStore.gateway.exception.WebfluxExceptionResponseStatusBuilder;
import com.bestStore.gateway.revokedTokenService.DelegationRevokeTokenService;
import com.bestStore.gateway.revokedTokenService.RevokeTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;
/**
 * Global WebFlux filter for API Gateway that handles JWT-based authentication.
 *
 * <p>
 * This filter inspects incoming requests, verifies the token if present, checks for revocation,
 * and enriches the request with user-specific headers extracted from the token.
 * </p>
 *
 * <p>
 * Behavior:
 * <ul>
 *     <li>If no token is present — request is allowed but marked with internal secret and request ID.</li>
 *     <li>If token is revoked — request is rejected with HTTP 401 Unauthorized.</li>
 *     <li>If token is valid — claims are extracted and injected into request headers.</li>
 *     <li>If token is invalid — request is rejected with HTTP 401 Unauthorized.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Headers injected:
 * <ul>
 *     <li>{@code X-Internal-Secret} — internal key from configuration</li>
 *     <li>{@code X-Request-Token-Id} — unique request ID</li>
 *     <li>plus user-related headers from {@link HeaderAdapter}</li>
 * </ul>
 * </p>
 *
 * <p>
 * @author Ihor Murashko
 * </p>
 */
public class GatewayAuthenticationFilter implements GlobalFilter {

    private final Logger log = LoggerFactory.getLogger(GatewayAuthenticationFilter.class);

    private final SecurityKeysProperties securityKeysProperties;
    private final JwtTokenProvider jwtTokenProvider;
    private final HeaderAdapter<HttpHeaders, String> headerAdapter;
    private final RevokeTokenService delegationRevokeTokenService;

    public GatewayAuthenticationFilter(SecurityKeysProperties securityKeysProperties, JwtTokenProvider jwtTokenProvider,
                                       HeaderAdapter<HttpHeaders, String> headerAdapter,
                                       RevokeTokenService delegationRevokeTokenService) {
        this.securityKeysProperties = securityKeysProperties;
        this.jwtTokenProvider = jwtTokenProvider;
        this.headerAdapter = headerAdapter;
        this.delegationRevokeTokenService = delegationRevokeTokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        log.debug("gateway filter: request received: {}", request.getPath());

        final String authHeader = request.getHeaders().getFirst(HeadersConstants.HEADER_AUTHENTICATION);
        log.debug("gateway filter: auth header: {}", authHeader);

//        HttpHeaders newHeaders = new HttpHeaders();
        MultiValueMap<String, String> newHeaders = new LinkedMultiValueMap<>();

        newHeaders.set(HeadersConstants.HEADER_INTERNAL_SECRET, securityKeysProperties.internal());
        newHeaders.set(HeadersConstants.HEADER_REQUEST_TOKEN_ID, UUID.randomUUID().toString());
        log.debug("gateway filter: set internal secret and request id");
        newHeaders.forEach((k,v)->log.debug("OUT HEADER: {} = {}", k, v));

        if (authHeader == null || !authHeader.startsWith(HeadersConstants.BEARER_PREFIX)) {
            log.info("Request without token");

            ServerHttpRequest mutatedRequest = request.mutate()
                    .headers(httpHeaders -> httpHeaders.putAll(newHeaders))
                    .build();

            log.debug("Final request headers:");
            mutatedRequest.getHeaders().forEach((k, v) -> log.debug(">> {} = {}", k, v));

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        }

        final String token = authHeader.substring(7);

        if (delegationRevokeTokenService.isTokenRevoked(TokenRedisKeyUtil.generateBase64RedisKey(token))) {
            log.warn("Attempt with revoked token: {}", token);

            return WebfluxExceptionResponseStatusBuilder.buildWebFluxExceptionResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    ExceptionMessageConstants.TOKEN_WAS_REVOKED,
                    "application/json"
            );
        }

        if (jwtTokenProvider.validateToken(token)) {
            HttpHeaders tokenHeaders = headerAdapter.convert(token);
            newHeaders.putAll(tokenHeaders);

            ServerHttpRequest mutatedRequest = request.mutate()
                    .headers(httpHeaders -> httpHeaders.putAll(newHeaders))
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        }

        log.warn("Invalid token received");
        return WebfluxExceptionResponseStatusBuilder.buildWebFluxExceptionResponse(
                exchange,
                HttpStatus.UNAUTHORIZED,
                ExceptionMessageConstants.TOKEN_IS_NOT_VALID,
                "application/json"
        );
    }
}
