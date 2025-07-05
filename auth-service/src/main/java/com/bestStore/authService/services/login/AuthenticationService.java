package com.bestStore.authService.services.login;

import com.common.lib.authModule.authDto.LoginCredentialsDto;
import com.common.lib.authModule.token.TokenDto;
import jakarta.validation.Valid;
import org.springframework.lang.NonNull;
@FunctionalInterface
public interface AuthenticationService {
    TokenDto login(@NonNull @Valid LoginCredentialsDto loginCredentialsDto);
}
