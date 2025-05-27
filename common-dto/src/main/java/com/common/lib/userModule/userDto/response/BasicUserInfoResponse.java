package com.common.lib.userModule.userDto.response;

import com.common.lib.userModule.roles.Role;

import java.util.Set;
/**
 * DTO representing basic user information.
 * Contains user ID and role for simplified user responses.
 *
 * @author Ihor Murashko
 */
public interface BasicUserInfoResponse {

    long id();

    String email();

    Set<? extends Role> roles();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();


}
