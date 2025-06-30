package com.bestStore.authService.registration;

import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import org.springframework.lang.NonNull;

public interface RegistrationService {

    boolean registration(@NonNull RegistrationCredentialsDto registrationCredentialsDto);


}
