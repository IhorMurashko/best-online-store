package com.example.microservices_auth.model;

<<<<<<< HEAD
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
>>>>>>> main
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

<<<<<<< HEAD
=======
@Getter
@Setter
@NoArgsConstructor
>>>>>>> main
public class CustomUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String username, String password, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

<<<<<<< HEAD
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

=======
>>>>>>> main
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
