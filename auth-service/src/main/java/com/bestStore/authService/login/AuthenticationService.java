package com.bestStore.authService.login;

import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import jakarta.validation.Valid;

public interface AuthenticationService {
    String login(@Valid RegistrationCredentialsDto registrationCredentialsDto);


}
