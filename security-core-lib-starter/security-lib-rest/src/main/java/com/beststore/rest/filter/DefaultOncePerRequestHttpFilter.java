package com.beststore.rest.filter;

import com.bestStore.core.constants.HeadersConstants;
import com.bestStore.core.constants.TokenType;
import com.bestStore.core.exceptions.claimsException.HeaderClaimException;
import com.bestStore.core.headerHandling.HeaderAdapter;
import com.bestStore.core.properties.SecurityKeysProperties;
import com.beststore.rest.context.dto.UserContext;
import com.beststore.rest.properties.RestTokenModeProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Custom {@link OncePerRequestFilter} for REST microservices that validates request headers
 * and builds Spring Security's {@link Authentication} context from user context.
 *
 * <p>
 * Основные задачи фильтра:
 * <ul>
 *     <li>Проверка заголовка {@code X-Internal-Secret}</li>
 *     <li>Проверка типа токена (ACCESS или REFRESH) и допустимости URI</li>
 *     <li>Извлечение {@link UserContext} из заголовков и установка {@link Authentication}</li>
 *     <li>Возврат 401 при ошибках</li>
 * </ul>
 * </p>
 *
 * <p>
 * Особенности:
 * <ul>
 *     <li>Поддержка режима {@code refresh-token-mode}</li>
 *     <li>Гибкость URI по списку разрешённых путей</li>
 *     <li>Безопасное логгирование и graceful error handling</li>
 * </ul>
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
public class DefaultOncePerRequestHttpFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(DefaultOncePerRequestHttpFilter.class);

    private final String internalSecretKey;
    private final HeaderAdapter<UserContext, HttpServletRequest> userContextHeaderAdapter;
    private final RestTokenModeProperties tokenMode;

    public DefaultOncePerRequestHttpFilter(SecurityKeysProperties securityKeysProperties,
                                           HeaderAdapter<UserContext, HttpServletRequest> userContextHeaderAdapter,
                                           RestTokenModeProperties tokenMode) {
        this.internalSecretKey = securityKeysProperties.internal();
        this.userContextHeaderAdapter = userContextHeaderAdapter;
        this.tokenMode = tokenMode;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        Collections.list(request.getHeaderNames()).forEach(name ->
                log.debug("Incoming header [{}] = {}", name, request.getHeader(name))
        );

        try {
            String internalSecret = request.getHeader(HeadersConstants.HEADER_INTERNAL_SECRET);


            if (!internalSecretKey.equals(internalSecret)) {
                log.warn("Invalid internal secret: {}", internalSecret);
                respondUnauthorized(response, "Invalid internal secret");
                return;
            }

            String tokenTypeHeader = request.getHeader(HeadersConstants.HEADER_TOKEN_TYPE);

            if (tokenTypeHeader == null) {
                filterChain.doFilter(request, response);
                return;
            }

            TokenType tokenType;
            try {
                tokenType = TokenType.valueOf(tokenTypeHeader);
            } catch (IllegalArgumentException ex) {
                log.warn("Invalid token type header: {}", tokenTypeHeader);
                respondUnauthorized(response, "Invalid token type");
                return;
            }

            String requestUri = request.getRequestURI();

            if (tokenType == TokenType.REFRESH) {

                if (!tokenMode.enabled()) {
                    log.warn("Refresh token used but refresh mode is disabled");
                    respondUnauthorized(response, "Refresh token not allowed in this service");
                    return;
                }


                if (!isAllowedRefreshTokenRequest(requestUri)) {

                    log.warn("Refresh token used on wrong path: {}", requestUri);
                    respondUnauthorized(response, "Refresh token only allowed on " + Arrays.stream(
                                    tokenMode.allowedRefreshAccess())
                            .map(path -> String.join(",", path))
                            .collect(Collectors.toSet()));
                    return;
                }

            } else if (tokenType == TokenType.ACCESS) {

                if (tokenMode.enabled() && isAllowedRefreshTokenRequest(requestUri)) {
                    log.warn("Access token used on refresh path: {}", requestUri);
                    respondUnauthorized(response, "Access token not allowed on " + Arrays.stream(
                                    tokenMode.allowedRefreshAccess())
                            .map(path -> String.join(",", path))
                            .collect(Collectors.toSet()));
                    return;
                }
            }

            UserContext userContext;
            try {
                userContext = userContextHeaderAdapter.convert(request);
            } catch (HeaderClaimException ex) {
                log.warn("Header conversion failed: {}", ex.getMessage());
                respondUnauthorized(response, "Invalid authentication headers");
                return;
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userContext.id(), null, userContext.roles());

            SecurityContextHolder.getContext().setAuthentication(auth);
            log.debug("Authenticated user ID: {}, roles: {}", userContext.id(), userContext.roles());

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            log.warn("Unexpected exception in filter: {}", ex.getMessage());
            respondUnauthorized(response, "Unexpected authentication error");
        }
    }

    private void respondUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }

    private boolean isAllowedRefreshTokenRequest(String requestUri) {
        Optional<String> allowedRefreshTokenRequest = Arrays.stream(tokenMode.allowedRefreshAccess())
                .filter(path -> path.equals(requestUri))
                .findFirst();

        return allowedRefreshTokenRequest.isPresent();
    }
}
