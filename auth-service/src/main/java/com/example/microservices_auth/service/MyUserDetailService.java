package com.example.microservices_auth.service;

import com.common.lib.authModule.authDto.BasicUserAuthenticationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/*
Custom implementation of UserDetailsService
that retrieves user details from an external USER-SERVICE
via REST using the user's email.
Used for authentication in Spring Security.
*/

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-User-Email", email);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<BasicUserAuthenticationResponseDto> response = restTemplate.exchange(
                    "http://USER-SERVICE/api/v1/user/get-by-email",
                    HttpMethod.GET,
                    entity,
                    BasicUserAuthenticationResponseDto.class
            );
            if (response.hasBody()) {
                return response.getBody();

            } else {
                throw new UsernameNotFoundException(email);
            }
        } catch (RestClientException e) {
            throw new UsernameNotFoundException("User service not available", e);
        }
    }
}
