package com.bestStore.authService.services.logout;

import com.common.lib.authModule.token.TokenDto;
import org.springframework.lang.NonNull;

@FunctionalInterface
public interface LogoutService {
    void logout(@NonNull TokenDto tokenDto);

}
