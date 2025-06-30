package com.bestStore.authService.login;

import com.bestStore.auth.UserFinderServiceGrpcGrpc;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {


    private final UserFinderServiceGrpcGrpc.UserFinderServiceGrpcBlockingStub blockingStub;

    @Override
    public String login(RegistrationCredentialsDto registrationCredentialsDto) {
        return "";
    }
}
