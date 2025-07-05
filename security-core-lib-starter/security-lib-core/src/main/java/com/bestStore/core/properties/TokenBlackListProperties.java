package com.bestStore.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "security-lib.black-list")
@Validated
public record TokenBlackListProperties(
        boolean enabled
) {
}
