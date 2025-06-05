package com.besstore.notificationService.exceptionHandler;

import com.besstore.notificationService.exception.NotificationBuilderWasNotFoundException;
import com.besstore.notificationService.exception.NotificationChannelWasNotFoundException;
import com.common.lib.exception.BaseExceptionResponse;
import com.common.lib.exception.ResponseExceptionProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Global exception handler for managing and responding to exceptions thrown within the application.
 * This class provides centralized exception handling by catching specific exceptions and
 * returning appropriate HTTP response entities.
 *
 * It utilizes Spring's {@code @RestControllerAdvice} to observe and gracefully handle exceptions
 * across the entire application. Each handler method is designated to catch a specific type of
 * exception and structure a corresponding {@code ResponseEntity} using {@link ResponseExceptionProvider}.
 *
 * This class employs the following exception handling strategies:
 * - {@link NotificationBuilderWasNotFoundException}: Captures cases where the notification builder is unavailable.
 * - {@link NotificationChannelWasNotFoundException}: Captures cases where the notification channel cannot be found.
 * - {@link RuntimeException}: Handles unanticipated runtime exceptions and returns a generic internal server error response.
 *
 * Each handler logs the error message before returning the corresponding response.
 *
 * An HTTP response is generated using a uniform structure defined by the {@link BaseExceptionResponse} record,
 * which outlines:
 * - HTTP status.
 * - Error message.
 * - Possible causes of the error.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(NotificationBuilderWasNotFoundException.class)
    public ResponseEntity<BaseExceptionResponse> handleNotificationBuilderWasNotFoundException(NotificationBuilderWasNotFoundException ex) {
        log.error(ex.getMessage());

        return ResponseExceptionProvider.buildResponse(
                HttpStatus.BAD_REQUEST,
                "Notification builder was not found",
                List.of(ex.getMessage())
        );
    }

    @ExceptionHandler(NotificationChannelWasNotFoundException.class)
    public ResponseEntity<BaseExceptionResponse> handleNotificationChannelWasNotFoundException(NotificationChannelWasNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseExceptionProvider.buildResponse(
                HttpStatus.BAD_REQUEST,
                "Notification channel was not found",
                List.of(ex.getMessage())
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseExceptionResponse> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage());
        return ResponseExceptionProvider.buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                List.of(ex.getMessage())
        );
    }
}