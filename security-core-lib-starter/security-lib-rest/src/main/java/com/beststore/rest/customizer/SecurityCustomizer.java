package com.beststore.rest.customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@FunctionalInterface
public interface SecurityCustomizer {
    void customize(HttpSecurity http) throws Exception;
}
