package com.example.microservices_auth.model;

import com.common.lib.userModule.userDto.response.BasicUserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private MyUserRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
        ResponseEntity<BasicUserInfoResponse> response = restTemplate.exchange(
                "http://USER-SERVICE/api/v1/user/get",
                HttpMethod.GET,
                new HttpEntity<>(Email),
                BasicUserInfoResponse.class
        );
        BasicUserInfoResponse basicUser = response.getBody();

        return

    };

    private String getRoles(MyUser user) {
        if (user.getRole() == null) {
            return "USER";
        }
        return user.getRole().toString();
    }
}
