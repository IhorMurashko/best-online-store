package com.matthew.project.api_gateway.jwt.filter;

import com.google.common.net.HttpHeaders;
import com.matthew.project.api_gateway.jwt.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


import java.util.List;

/*
Gateway filter that checks for a valid JWT in the Authorization header.
If valid, it extracts user info (username, role, id) and adds them to request headers.
*/

@Component
public class JwtAuthFilter implements WebFilter {

    @Autowired
    private JwtService jwtService;

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String path = exchange.getRequest().getURI().getPath();
        log.debug("JWT AuthFilter: path={}, Authorization header={}", path, authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        try {
            if (!jwtService.isTokenValid(token)) {
                log.warn("[Gateway] Invalid JWT token");
                return chain.filter(exchange);
            }

            String username = jwtService.extractUsername(token);
            String role = jwtService.extractRoles(token);
            String id = jwtService.extractId(token);

            if (username == null || role == null || id == null) {
                log.warn("[Gateway] Missing claims in JWT");
                return chain.filter(exchange);
            }

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

            log.info("[Gateway] Setting headers - X-User-Name: {}, X-User-Role: {}", username, role);

            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .header("X-User-Name", username)
                    .header("X-User-Role", role)
                    .header("X-User-Id", id)
                    .build();


            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();


            return chain.filter(modifiedExchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));

        } catch (Exception e) {
            log.error("[Gateway] JWT processing exception: {}. Proceeding without auth.", e.getMessage());
            return chain.filter(exchange);
        }
    }
}
