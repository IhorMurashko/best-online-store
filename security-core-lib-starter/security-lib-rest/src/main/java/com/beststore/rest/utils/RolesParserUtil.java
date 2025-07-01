package com.beststore.rest.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RolesParserUtil {
    private RolesParserUtil() {
    }


    public static Set<SimpleGrantedAuthority> getRolesListFromString(String roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptySet();
        }

        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .filter(r -> !r.isBlank())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Set::copyOf));
    }


    public static Set<SimpleGrantedAuthority> getRolesListFromList(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptySet();
        }

        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

}
