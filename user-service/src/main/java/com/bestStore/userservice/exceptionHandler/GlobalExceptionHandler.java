package com.bestStore.userservice.exceptionHandler;

import com.bestStore.userservice.exceptions.UserNotFoundException;
import com.common.lib.exception.BaseExceptionResponse;
import com.common.lib.exception.InvalidAuthCredentials;
import com.common.lib.exception.RequestDoesNotContainsHeader;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {

        log.warn(e.getMessage());

        BaseExceptionResponse exceptionResponse = new BaseExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    //invalid header
    @ExceptionHandler(RequestDoesNotContainsHeader.class)
    public ResponseEntity<BaseExceptionResponse> handleRequestDoesNotContainsHeader(RequestDoesNotContainsHeader ex) {
        log.warn(ex.getMessage());

        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }


    //invalid credentials
    @ExceptionHandler(InvalidAuthCredentials.class)
    public ResponseEntity<BaseExceptionResponse> handleInvalidAuthCredentials(InvalidAuthCredentials ex) {
        log.warn(ex.getMessage());


        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    //  @Valid validation errors (DTO)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseExceptionResponse> handleValidationError(MethodArgumentNotValidException ex) {

        log.warn("Validation failed: {}", ex.getMessage());

        List<String> causes = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.toList());

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", causes);
    }

    private String formatFieldError(FieldError error) {
        return String.format("Field '%s' %s", error.getField(), error.getDefaultMessage());
    }

    // Bean validation errors (@Validated on controller)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseExceptionResponse> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Constraint violation: {}", ex.getMessage());

        List<String> causes = ex.getConstraintViolations().stream()
                .map(v -> String.format("%s %s", v.getPropertyPath(), v.getMessage()))
                .collect(Collectors.toList());

        return buildResponse(HttpStatus.BAD_REQUEST, "Constraint violation", causes);
    }


    // Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseExceptionResponse> handleGeneral(Exception ex) {
        log.error("Unhandled exception", ex);

        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", List.of(ex.getMessage()));
    }

    // Helper
    private ResponseEntity<BaseExceptionResponse> buildResponse(HttpStatus status, String message, List<String> causes) {
        BaseExceptionResponse response = new BaseExceptionResponse(
                status.value(),
                message,
                causes
        );
        return new ResponseEntity<>(response, status);
    }

}
