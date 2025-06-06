package com.common.lib.userModule.roles;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
/**
 * Default role enumeration.
 * Contains common roles used across the application.
 *
 * @author Ihor Murashko
 */
public enum Role implements BasicUserRoles {
    ROLE_USER, ROLE_ADMIN;

    @Override
    public boolean isValid(String role) {
        return Arrays.stream(values()).anyMatch(r ->
                r.name().equals(role));
    }
    @JsonCreator
    public static Role fromString(String key) {
        return Role.valueOf(key.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}

