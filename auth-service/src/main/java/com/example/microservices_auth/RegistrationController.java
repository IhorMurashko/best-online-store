package com.example.microservices_auth;

import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import com.example.microservices_auth.model.MyUser;
import com.example.microservices_auth.model.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RegistrationController {

    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/register/user")
    public ResponseEntity<?> createUser(@RequestBody RegistrationCredentialsDto dto) {
        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    "http://USER-SERVICE/api/v1/user/create",
                    HttpMethod.POST,
                    new HttpEntity<>(dto),
                    Void.class
            );

            return ResponseEntity.status(response.getStatusCode()).build();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode()).build();
        }
    }
}
