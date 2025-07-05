package com.bestStore.authService.services.registration;

import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import jakarta.validation.Valid;
import org.springframework.lang.NonNull;

public interface RegistrationService {
    void registration(@NonNull @Valid RegistrationCredentialsDto registrationCredentialsDto);
}
