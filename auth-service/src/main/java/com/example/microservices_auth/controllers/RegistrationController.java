package com.example.microservices_auth.controllers;

import com.bestStore.userService.exceptions.ExceptionMessageProvider;
import com.bestStore.userService.utils.UserFieldAdapter;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import com.common.lib.exception.InvalidAuthCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/*
Handles user registration by validating passwords, hashing the password,
and forwarding the user data to the external USER-SERVICE.
Returns the status code from the user service or propagates HTTP errors if they occur.
*/

@Slf4j
@RestController
public class RegistrationController {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/register/user")
    public ResponseEntity<?> createUser(@RequestBody RegistrationCredentialsDto dto) {
        if (!dto.password().equals(dto.confirmationPassword())) {
            log.error("Passwords do not match");
            throw new InvalidAuthCredentials(String.format(ExceptionMessageProvider.PASSWORDS_DONT_MATCH));
        }

        String email = UserFieldAdapter.toLower(dto.email());
        String hashedPassword = passwordEncoder.encode(dto.password());
        RegistrationCredentialsDto finalDto = new RegistrationCredentialsDto(email, hashedPassword, hashedPassword);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    "http://USER-SERVICE/api/v1/user/create",
                    HttpMethod.POST,
                    new HttpEntity<>(finalDto),
                    Void.class
            );

            return ResponseEntity.status(response.getStatusCode()).build();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("User service error: {}", ex.getMessage());
            return ResponseEntity.status(ex.getStatusCode()).build();
        }
    }
}
