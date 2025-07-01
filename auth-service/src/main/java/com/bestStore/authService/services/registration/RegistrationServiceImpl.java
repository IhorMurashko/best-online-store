package com.bestStore.authService.services.registration;

import com.bestStore.AuthenticationCredentials;
import com.bestStore.Result;
import com.bestStore.auth.RegistrationServiceGrpc;
import com.bestStore.authService.exceptions.ExceptionConstantMessage;
import com.bestStore.authService.exceptions.exception.CredentialsException;
import com.bestStore.authService.exceptions.utils.StatusRuntimeExceptionToRuntimeExceptionConvertor;
import com.common.lib.authModule.authDto.RegistrationCredentialsDto;
import io.grpc.StatusRuntimeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class RegistrationServiceImpl implements RegistrationService {

    private final BCryptPasswordEncoder cryptPasswordEncoder;

    @GrpcClient("user-service")
    private RegistrationServiceGrpc.RegistrationServiceBlockingStub serviceBlockingStub;

    private RegistrationServiceGrpc.RegistrationServiceBlockingStub stub() {
        return serviceBlockingStub.withDeadlineAfter(5, TimeUnit.SECONDS);
    }


    @Override
    public boolean registration(@NonNull @Valid RegistrationCredentialsDto registrationCredentialsDto) {

        if (!Objects.equals(registrationCredentialsDto.password(), registrationCredentialsDto.confirmationPassword())) {
            log.error("Passwords do not match");
            throw new CredentialsException(ExceptionConstantMessage.PASSWORDS_DONT_MATCH);
        }

        String crypdedPassword = cryptPasswordEncoder.encode(registrationCredentialsDto.password());
        log.debug("Password encoded successfully");

        boolean result = false;
        try {
            AuthenticationCredentials registrationRequest = AuthenticationCredentials.newBuilder()
                    .setEmail(registrationCredentialsDto.email())
                    .setPassword(crypdedPassword)
                    .build();

            log.debug("Registration request: {}", registrationRequest);

            Result registration = stub().registration(registrationRequest);
            log.debug("Registration result: {}", registration);

            result = registration.getResult();
        } catch (StatusRuntimeException ex) {
            log.warn("gRPC call failed: {}", ex.getStatus(), ex);

            throw StatusRuntimeExceptionToRuntimeExceptionConvertor
                    .convert(ex);
        }

        //todo: send congratulation latter
        return result;
    }
}
