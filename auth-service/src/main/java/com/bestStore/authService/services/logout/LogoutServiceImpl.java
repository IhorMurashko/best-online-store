package com.bestStore.authService.services.logout;

import com.bestStore.core.revokedTokenService.RevokeTokenService;
import com.common.lib.authModule.token.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutServiceImpl implements LogoutService {

    private final RevokeTokenService revokeTokenService;

    @Override
    public void logout(@NonNull TokenDto tokenDto) {
        revokeTokenService.revokeToken(tokenDto.accessToken(), tokenDto.refreshToken());
    }
}
