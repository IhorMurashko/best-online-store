package com.common.lib.userModule.roles;
/**
 * Common interface to represent different role enums used in the application.
 * Can be implemented by specific role enums like Role, AdminRole, etc.
 *
 * @author Ihor Murashko
 */
public interface BasicUserRoles {
    default String getRole() {
        return toString();
    }

    boolean isValid(String role);
}
