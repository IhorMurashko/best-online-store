package com.bestStore.userservice.services.authGrpc;

import com.bestStore.AuthenticationCredentials;
import com.bestStore.Result;
import com.bestStore.auth.RegistrationServiceGrpc;
import com.bestStore.userservice.exceptions.EmailAlreadyExistException;
import com.bestStore.userservice.exceptions.ExceptionMessageProvider;
import com.bestStore.userservice.model.User;
import com.bestStore.userservice.services.userCrudService.UserCrudService;
import com.bestStore.userservice.utils.UserFieldAdapter;
import com.common.lib.exception.InvalidAuthCredentials;
import com.common.lib.userModule.AuthProvider.AuthProvider;
import com.common.lib.userModule.constraint.FieldValidationPatterns;
import com.common.lib.userModule.roles.Role;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Set;
import java.util.regex.Pattern;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationService extends RegistrationServiceGrpc.RegistrationServiceImplBase {

    private final UserCrudService userCrudService;

    @Override
    public void registration(AuthenticationCredentials request, StreamObserver<Result> responseObserver) {
        try {
            String email = UserFieldAdapter.toLower(request.getEmail());

            if (!Pattern.matches(FieldValidationPatterns.EMAIL_PATTERN, email)) {
                log.error("Invalid email format {}", email);
                throw new InvalidAuthCredentials(
                        FieldValidationPatterns.EMAIL_MESSAGE
                );
            }

            if (userCrudService.isEmailExist(email)) {
                log.error("Email already exist {}", email);
                throw new EmailAlreadyExistException(
                        String.format(
                                ExceptionMessageProvider.EMAIL_ALREADY_EXIST, email)
                );
            }


            User user = new User(
                    UserFieldAdapter.toLower(email), request.getPassword(),
                    AuthProvider.LOCAL, null,
                    true, true,
                    true, true, Set.of(Role.ROLE_USER)
            );

            log.debug("Registering user {}", user);
            userCrudService.save(user);

            responseObserver.onNext(
                    Result.newBuilder()
                            .setResult(true)
                            .build());
            responseObserver.onCompleted();
        } catch (Exception ex) {
            log.error("Registration failed: {}", ex.getMessage(), ex);
            responseObserver.onError(ex);
        }
    }
}
