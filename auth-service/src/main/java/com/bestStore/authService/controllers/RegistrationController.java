package com.bestStore.authService.controllers;

import com.bestStore.authService.services.registration.RegistrationService;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reg")
@RequiredArgsConstructor
@Validated
@Slf4j
@PreAuthorize("isAnonymous()")
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/sing-up")
    public ResponseEntity<HttpStatus> singUp(@RequestBody @Valid RegistrationCredentialsDto credentials) {
        registrationService.registration(credentials);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
