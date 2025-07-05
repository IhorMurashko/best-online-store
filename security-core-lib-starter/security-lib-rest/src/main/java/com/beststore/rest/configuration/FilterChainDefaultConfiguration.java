package com.beststore.rest.configuration;

import com.beststore.rest.customizer.SecurityCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

/**
 * Configures the default Spring Security filter chain for REST microservices.
 *
 * <p>
 * Applies stateless authentication with:
 * <ul>
 *     <li>Disabled CSRF, form login, and basic auth</li>
 *     <li>Permissive CORS configuration</li>
 *     <li>Stateless session management</li>
 *     <li>Custom {@link OncePerRequestFilter} to extract user context</li>
 *     <li>Pluggable {@link AuthenticationEntryPoint} and {@link AccessDeniedHandler}</li>
 * </ul>
 * </p>
 *
 * <p>
 * This configuration is conditional on the absence of a custom {@link SecurityFilterChain}.
 * </p>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
@Configuration
@EnableMethodSecurity
public class FilterChainDefaultConfiguration {

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final OncePerRequestFilter defaultOnePerRequestHttpFilter;
    private final List<SecurityCustomizer> customizers;

    public FilterChainDefaultConfiguration(AccessDeniedHandler accessDeniedHandler,
                                           AuthenticationEntryPoint authenticationEntryPoint,
                                           OncePerRequestFilter defaultOnePerRequestHttpFilter, List<SecurityCustomizer> customizers) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.defaultOnePerRequestHttpFilter = defaultOnePerRequestHttpFilter;
        this.customizers = customizers;
    }

    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of("*"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(defaultOnePerRequestHttpFilter, UsernamePasswordAuthenticationFilter.class);

        if (customizers != null) {
            for (SecurityCustomizer customizer : customizers) {
                customizer.customize(http);
            }
        }

        return http.build();
    }
}
