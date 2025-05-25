package com.common.lib.userModule.userDto.response;

import com.common.lib.userModule.roles.Role;

import java.io.Serializable;
import java.util.Set;
/**
 * Wrapper DTO for returning a single BasicUserInfo object in responses.
 *
 * @author Ihor Murashko
 */
public record BasicUserInfoResponseDto(
        long id,
        String email,
        Set<Role> roles,
        boolean isAccountNonExpired,
        boolean isAccountNonLocked,
        boolean isCredentialsNonExpired,
        boolean isEnabled

) implements Serializable, BasicUserInfoResponse {
}
