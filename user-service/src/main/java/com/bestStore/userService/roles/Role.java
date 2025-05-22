package com.bestStore.userService.roles;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum representing application-level user roles.
 *
 * <p>Used for access control in Spring Security. Roles are stored in the database
 * as part of the user's role set, using an ElementCollection.</p>
 *
 * Examples:
 * - CUSTOMER
 * - ADMIN
 * - MANAGER
 *
 * @author Ihor MUrashko
 */



public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
