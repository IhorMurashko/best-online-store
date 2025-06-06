package com.common.lib.authModule.authDto;

import com.common.lib.userModule.roles.Role;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Wrapper DTO for returning a single BasicUserInfo object for spring security authentication in responses.
 *
 * @author Matfei Hasych
 */
public class BasicUserAuthenticationResponseDto implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private Set<Role> roles;

    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities;

    public BasicUserAuthenticationResponseDto(UserDetails userDetails) {

        if (userDetails instanceof BasicUserAuthenticationResponseDto customUser) {
            this.id = customUser.getId();
        }
        this.email = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.roles = userDetails.getAuthorities().stream()
                .map(auth -> {
                    String roleName = auth.getAuthority().replace("ROLE_", "");
                    try {
                        return Role.valueOf(roleName);
                    } catch (IllegalArgumentException e) {
                        // Логируем или обрабатываем неизвестную роль
                        System.err.println("Unknown role: " + roleName);
                        return null; // или выбрасываем свое исключение
                    }
                })
                .filter(r -> r != null)
                .collect(Collectors.toSet());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public BasicUserAuthenticationResponseDto(Long id, String email, String password, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;

    }
    public BasicUserAuthenticationResponseDto() {
    }

    @JsonGetter("roles")
    public Set<String> getRolesAsStrings() {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::name)
                .collect(Collectors.toSet());
    }

    @JsonSetter("roles")
    public void setRolesFromStrings(Set<String> rolesStr) {
        if (rolesStr == null) {
            this.roles = null;
        } else {
            this.roles = rolesStr.stream()
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());
        }
    }
}
