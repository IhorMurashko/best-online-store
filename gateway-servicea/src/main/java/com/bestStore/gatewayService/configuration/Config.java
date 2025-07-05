package com.bestStore.gatewayService.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("auth-service",
                        route -> route.path("/api/*/auth/**",
                                        "/api/*/reg/**")
                                .uri("http://localhost:8081")
                )
                .build();
    }
}
