package com.bestStore.userService.controllers;


import com.bestStore.userService.services.auth.AuthService;
import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import com.common.lib.userModule.roles.Role;
import com.common.lib.userModule.userDto.response.BasicUserInfoResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/user-auth")
@RequiredArgsConstructor
@Validated
public class AuthUserController {

    private final AuthService authService;


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid RegistrationCredentialsDto registrationCredentialsDto) {

        authService.registration(registrationCredentialsDto, Set.of(Role.ROLE_USER));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("login")
    public ResponseEntity<BasicUserInfoResponse> login(@RequestBody @Valid LoginCredentialsDto loginCredentialsDto) {

        BasicUserInfoResponse login = authService.login(loginCredentialsDto);

        return ResponseEntity.ok(login);
    }


    @GetMapping("/me/{id}")
    public ResponseEntity<BasicUserInfoResponse> getCurrentUser(@PathVariable @Positive Long id) {

        BasicUserInfoResponse currentUserInfo
                = authService.getCurrentUserInfo(id);

        return ResponseEntity.ok(currentUserInfo);

    }


}
