package com.bestStore.userservice.exceptionHandler;

import com.bestStore.userservice.exceptions.EmailAlreadyExistException;
import com.bestStore.userservice.exceptions.UserNotFoundException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.springframework.lang.NonNull;

@GrpcAdvice
@Slf4j
public class GlobalGrpcExceptionHandler {


    @GrpcExceptionHandler(UserNotFoundException.class)
    public StatusRuntimeException handleUserNotFound(@NonNull UserNotFoundException ex) {
        log.warn("UserNotFoundException handled: {}", ex.getMessage());
        return Status.NOT_FOUND
                .withDescription(ex.getMessage())
                .withCause(ex)
                .asRuntimeException();
    }

    @GrpcExceptionHandler(EmailAlreadyExistException.class)
    public StatusRuntimeException handleEmailAlreadyExists(@NonNull EmailAlreadyExistException ex) {
        log.warn("Email already exist handled: {}", ex.getMessage());
        return Status.ALREADY_EXISTS
                .withDescription(ex.getMessage())
                .withCause(ex)
                .asRuntimeException();
    }

    @GrpcExceptionHandler(ValidationException.class)
    public StatusRuntimeException handleValidation(@NonNull ValidationException ex) {
        log.warn("ValidationException handled: {}", ex.getMessage());
        return Status.INVALID_ARGUMENT
                .withDescription(ex.getMessage())
                .withCause(ex)
                .asRuntimeException();
    }

    @GrpcExceptionHandler(IllegalArgumentException.class)
    public StatusRuntimeException handleIllegalArgument(@NonNull IllegalArgumentException ex) {
        log.warn("IllegalArgumentException handled: {}", ex.getMessage());
        return Status.INVALID_ARGUMENT
                .withDescription("Некорректный аргумент: " + ex.getMessage())
                .withCause(ex)
                .asRuntimeException();
    }

    @GrpcExceptionHandler(RuntimeException.class)
    public StatusRuntimeException handleUnexpected(@NonNull RuntimeException ex) {
        log.error("Unexpected exception: {}", ex.getMessage(), ex);
        return Status.INTERNAL
                .withDescription("Внутренняя ошибка сервера")
                .withCause(ex)
                .asRuntimeException();
    }
}
