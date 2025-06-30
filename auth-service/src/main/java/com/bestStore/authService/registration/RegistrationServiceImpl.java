package com.bestStore.authService.registration;

import com.bestStore.AuthenticationCredentials;
import com.bestStore.Result;
import com.bestStore.auth.RegistrationServiceGrpcGrpc;
import com.bestStore.authService.exceptions.CredentialsException;
import com.bestStore.authService.exceptions.ExceptionConstantMessage;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import io.grpc.StatusRuntimeException;
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
    private RegistrationServiceGrpcGrpc.RegistrationServiceGrpcBlockingStub serviceBlockingStub;

    @Override
    public boolean registration(@NonNull @Valid RegistrationCredentialsDto registrationCredentialsDto) {

        if (!Objects.equals(registrationCredentialsDto.password(), registrationCredentialsDto.confirmationPassword())) {
            throw new CredentialsException(ExceptionConstantMessage.PASSWORDS_DONT_MATCH);
        }

//        todo:crypt password
        String crypdedPassword = registrationCredentialsDto.password();

        boolean result = false;
        try {
            AuthenticationCredentials registrationRequest = AuthenticationCredentials.newBuilder()
                    .setEmail(registrationCredentialsDto.email())
                    .setPassword(crypdedPassword)
                    .build();

            Result registration = serviceBlockingStub.registration(registrationRequest);

            result = registration.getResult();
        } catch (StatusRuntimeException e) {
            log.warn("gRPC call failed: {}", e.getStatus(), e);

            throw new CredentialsException(ExceptionConstantMessage.REGISTRATION_EXCEPTION
                    + "  message: " + e.getStatus().getDescription());
        }
        return result;
    }
}
