package com.bestStore.userservice.services.authGrpc;

import com.bestStore.ByEmail;
import com.bestStore.ById;
import com.bestStore.UserCredentials;
import com.bestStore.auth.UserFinderServiceGrpc;
import com.bestStore.userservice.exceptions.ExceptionMessageProvider;
import com.bestStore.userservice.exceptions.UserNotFoundException;
import com.bestStore.userservice.model.User;
import com.bestStore.userservice.services.userCrudService.UserCrudService;
import com.bestStore.userservice.utils.UserFieldAdapter;
import com.common.lib.userModule.roles.Role;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class UserFinderService extends UserFinderServiceGrpc.UserFinderServiceImplBase {


    private final UserCrudService userCrudService;

    @Override
    public void getByEmail(ByEmail request, StreamObserver<UserCredentials> responseObserver) {
        try {
            String email = UserFieldAdapter.toLower(request.getEmail());
            log.debug("getByEmail {}", email);

            User user = userCrudService.findByEmail(email).orElseThrow(
                    () -> new UserNotFoundException(
                            String.format(
                                    ExceptionMessageProvider.USER_EMAIL_NOT_FOUND, email
                            )
                    )
            );
            log.debug("user by email {} was found", user);

            UserCredentials userCredentials = toUserCredentials(user);

            responseObserver.onNext(userCredentials);
            responseObserver.onCompleted();
        } catch (Exception ex) {
            log.error("Registration failed: {}", ex.getMessage(), ex);
            responseObserver.onError(ex);
        }
    }

    @Override
    public void getById(ById request, StreamObserver<UserCredentials> responseObserver) {
        try {

            long id = request.getId();
            log.debug("getById {}", id);

            User user = userCrudService.findById(id).orElseThrow(
                    () -> new UserNotFoundException(
                            String.format(ExceptionMessageProvider.USER_ID_NOT_FOUND, id))
            );
            log.debug("user by was found");

            UserCredentials userCredentials = toUserCredentials(user);

            responseObserver.onNext(userCredentials);
            responseObserver.onCompleted();
        } catch (Exception ex) {
            log.error("Registration failed: {}", ex.getMessage(), ex);
            responseObserver.onError(ex);
        }
    }


    private UserCredentials toUserCredentials(User user) {
        return UserCredentials.newBuilder()
                .setId(String.valueOf(user.getId()))
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .addAllRoles(user.getRoles().stream()
                        .map(Role::name).toList())
                .setIsAccountNonExpired(user.isAccountNonExpired())
                .setIsAccountNonLocked(user.isAccountNonLocked())
                .setIsCredentialsNonExpired(user.isCredentialsNonExpired())
                .setIsEnabled(user.isEnabled())
                .build();
    }
}
