package com.common.lib.userModule.userDto.response;

import com.common.lib.userModule.roles.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO that contains complete user profile information.
 * Intended to be used in internal services or admin-facing APIs.
 *
 * @author Ihor Murashko
 */
public record UserFullInfoResponseDto(BasicUserInfoResponseDto basicUserInfoResponseDto, LocalDateTime createdAt,
                                      LocalDateTime updatedAt, String firstName, String lastName, String phoneNumber,
                                      String country, String city, String streetName, String houseNumber,
                                      String zipCode) implements Serializable, BasicUserInfoResponse {
    @JsonIgnore
    @Override
    public long id() {
        return this.basicUserInfoResponseDto.id();
    }

    @JsonIgnore
    @Override
    public String email() {
        return this.basicUserInfoResponseDto.email();
    }

    @JsonIgnore
    @Override
    public Set<? extends Role> roles() {
        return this.basicUserInfoResponseDto.roles();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.basicUserInfoResponseDto.isAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.basicUserInfoResponseDto.isAccountNonLocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.basicUserInfoResponseDto.isCredentialsNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.basicUserInfoResponseDto.isEnabled();
    }
}
