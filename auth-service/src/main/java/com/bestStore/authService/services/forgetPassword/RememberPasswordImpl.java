package com.bestStore.authService.services.forgetPassword;

import com.bestStore.AuthenticationCredentials;
import com.bestStore.Result;
import com.bestStore.auth.RememberPasswordServiceGrpc;
import com.bestStore.authService.constatnts.GrpcClientConstant;
import com.bestStore.authService.exceptions.utils.StatusRuntimeExceptionToRuntimeExceptionConvertor;
import com.bestStore.authService.utils.RandomPasswordGenerator;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RememberPasswordImpl implements RememberPassword {

    private final PasswordEncoder passwordEncoder;

    @GrpcClient(GrpcClientConstant.USER_GRPC_CLIENT_NAME)
    private RememberPasswordServiceGrpc.RememberPasswordServiceBlockingStub blockingStub;

    private RememberPasswordServiceGrpc.RememberPasswordServiceBlockingStub stub() {
        return blockingStub.withDeadlineAfter(5, TimeUnit.SECONDS);
    }

    @Override
    public boolean resetPassword(String email) {
        Result result;

        try {
            String newCryptedPassword = passwordEncoder.encode(
                    RandomPasswordGenerator.generatePassword()
            );

            AuthenticationCredentials authenticationCredentials
                    = AuthenticationCredentials.newBuilder()
                    .setEmail(email.toLowerCase())
                    .setPassword(newCryptedPassword)
                    .build();

            result = stub().rememberPassword(authenticationCredentials);

        } catch (StatusRuntimeException ex) {
            log.warn("gRPC call failed: {}", ex.getStatus(), ex);

            throw StatusRuntimeExceptionToRuntimeExceptionConvertor
                    .convert(ex);
        }
//todo: send latter

        return result.getResult();
    }
}
