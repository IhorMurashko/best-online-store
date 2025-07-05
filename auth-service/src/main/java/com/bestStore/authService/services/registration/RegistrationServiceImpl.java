package com.bestStore.authService.services.registration;

import com.bestStore.AuthenticationCredentials;
import com.bestStore.Result;
import com.bestStore.auth.RegistrationServiceGrpc;
import com.bestStore.authService.constatnts.GrpcClientConstant;
import com.bestStore.authService.exceptions.ExceptionConstantMessage;
import com.bestStore.authService.exceptions.exception.CredentialsException;
import com.bestStore.authService.exceptions.utils.StatusRuntimeExceptionToRuntimeExceptionConvertor;
import com.bestStore.authService.services.notification.NotificationSenderService;
import com.bestStore.authService.utils.NotificationLetterBuilder;
import com.bestStore.notification.Letter;
import com.bestStore.notification.NotificationChannel;
import com.bestStore.notification.NotificationType;
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
    private final NotificationSenderService notificationSenderService;


    @GrpcClient(GrpcClientConstant.USER_GRPC_CLIENT_NAME)
    private RegistrationServiceGrpc.RegistrationServiceBlockingStub serviceBlockingStub;

    private RegistrationServiceGrpc.RegistrationServiceBlockingStub stub() {
        return serviceBlockingStub.withDeadlineAfter(5, TimeUnit.SECONDS);
    }


    @Override
    public void registration(@NonNull @Valid RegistrationCredentialsDto registrationCredentialsDto) {

        if (!Objects.equals(registrationCredentialsDto.password(), registrationCredentialsDto.confirmationPassword())) {
            log.error("Passwords do not match");
            throw new CredentialsException(ExceptionConstantMessage.PASSWORDS_DONT_MATCH);
        }

        String crypdedPassword = cryptPasswordEncoder.encode(registrationCredentialsDto.password());
        log.debug("Password encoded successfully");

        try {
            AuthenticationCredentials registrationRequest = AuthenticationCredentials.newBuilder()
                    .setEmail(registrationCredentialsDto.email().toLowerCase())
                    .setPassword(crypdedPassword)
                    .build();

            log.debug("Registration request: {}", registrationRequest);

            Result registration = stub().registration(registrationRequest);
            log.debug("Registration result: {}", registration);


            Letter congratiolationRegistrationLetter = NotificationLetterBuilder
                    .builder(NotificationType.REGISTRATION,
                            NotificationChannel.EMAIL,
                            registrationRequest.getEmail(),
                            "welcome ot our store",
                            null);

//            notificationSenderService.sendNotification(congratiolationRegistrationLetter, true);

        } catch (StatusRuntimeException ex) {
            log.warn("gRPC call failed: {}", ex.getStatus(), ex);
            throw StatusRuntimeExceptionToRuntimeExceptionConvertor
                    .convert(ex);
        }
    }
}
