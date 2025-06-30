package com.beststore.rest.enable;

import com.beststore.rest.configuration.FilterChainDefaultConfiguration;
import com.beststore.rest.configuration.RestSecurityDefaultConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Enables REST-oriented security configuration in a Spring Boot microservice.
 *
 * <p>
 * Imports the default {@link RestSecurityDefaultConfiguration} and {@link FilterChainDefaultConfiguration}
 * to activate token validation, header-based user context resolution, and request filtering.
 * </p>
 *
 * <pre>
 * &#64;EnableRestSecurity
 * &#64;SpringBootApplication
 * public class MyServiceApplication {
 * }
 * </pre>
 *
 * @author Ihor Murashko
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RestSecurityDefaultConfiguration.class,
        FilterChainDefaultConfiguration.class})
public @interface EnableRestSecurity {
}