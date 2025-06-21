package com.besstore.gatewayService.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${services.test-service.path}")
    String testServicePath;

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("test-service",
                        r ->
                                r.path("/test/**")
                                        .uri(testServicePath)
                )
                .build();
    }
}
