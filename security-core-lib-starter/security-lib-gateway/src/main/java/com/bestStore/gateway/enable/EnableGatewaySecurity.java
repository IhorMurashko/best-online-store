package com.bestStore.gateway.enable;


import com.bestStore.gateway.configuration.GatewaySecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Enables gateway-level security configuration by importing {@link GatewaySecurityConfiguration}.
 *
 * <p>
 * Use this annotation in your Spring Boot application to activate JWT-based authentication
 * and header enrichment for API Gateway layers.
 * </p>
 *
 * <pre>
 * &#64;EnableGatewaySecurity
 * &#64;SpringBootApplication
 * public class ApiGatewayApplication {
 * }
 * </pre>
 *
 * <p>
 * Author: Ihor Murashko
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(GatewaySecurityConfiguration.class)
public @interface EnableGatewaySecurity {
}
