package com.bestStore.authService.controllers;

import com.bestStore.authService.services.login.AuthenticationService;
import com.bestStore.authService.services.logout.LogoutService;
import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.common.lib.authModule.token.TokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/sing-in")
    public ResponseEntity<TokenDto> login(@NonNull @RequestBody @Valid LoginCredentialsDto loginCredentialsDto) {
        TokenDto tokenDto = authenticationService.login(loginCredentialsDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/google")
    public ResponseEntity<String> googleSingIn() {
        return ResponseEntity.ok("hello from social endpoint");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestBody @Valid TokenDto tokenDto) {
        logoutService.logout(tokenDto);
        return ResponseEntity.ok().build();
    }
}
