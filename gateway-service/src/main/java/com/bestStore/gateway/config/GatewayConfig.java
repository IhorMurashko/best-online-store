package com.bestStore.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining routes in Spring Cloud Gateway.
 * <p>
 * It maps incoming requests to the appropriate backend microservices
 * hosted on Render based on the request path.
 */
@Configuration
public class GatewayConfig {

    /**
     * Defines custom routes for the gateway.
     *
     * @param builder RouteLocatorBuilder provided by Spring Cloud Gateway
     * @return RouteLocator containing route definitions
     */
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        return builder.routes()

                // Route requests to the User Service
                .route("user-service",
                        request -> request.path("/api/v1/user/**")
                                .uri("https://user-service-azcf.onrender.com")
                )

                // Route requests to the Auth Service
                .route("auth-service",
                        request -> request.path("/api/v1/auth/**")
                                .uri("https://auth-service-qll0.onrender.com")

                )

                .build();
    }

}
