package com.bestStore.registration;

import com.bestStore.AuthenticationCredentials;
import com.bestStore.Result;
import com.bestStore.auth.RegistrationServiceGrpc;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class RegistrationServiceImpl implements RegistrationService {

    @GrpcClient("user-service")
    private final RegistrationServiceGrpc.RegistrationServiceBlockingStub serviceBlockingStub;

    @Override
    public boolean registration(@NonNull @Valid RegistrationCredentialsDto registrationCredentialsDto) {

        if (!Objects.equals(registrationCredentialsDto.password(), registrationCredentialsDto.password())) {
            throw new CredentialsException(ExceptionConstantMessage.PASSWORDS_DONT_MATCH);
        }

//        todo:crypt password
        String crypdedPaswword = registrationCredentialsDto.password();

        boolean result = false;
        try {
            AuthenticationCredentials registrationRequest = AuthenticationCredentials.newBuilder()
                    .setEmail(registrationCredentialsDto.email())
                    .setPassword(crypdedPaswword)
                    .build();

            Result registration = serviceBlockingStub.registration(registrationRequest);

            result = registration.getResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return result;
    }
}
