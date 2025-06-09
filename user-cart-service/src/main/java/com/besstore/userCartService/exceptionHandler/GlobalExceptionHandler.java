package com.besstore.userCartService.exceptionHandler;

import com.besstore.userCartService.exception.UserCartNotFoundException;
import com.common.lib.exception.BaseExceptionResponse;
import com.common.lib.exception.RequestDoesNotContainsHeader;
import com.common.lib.exception.ResponseExceptionProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserCartNotFoundException.class)
    public ResponseEntity<BaseExceptionResponse> handleUserCartNotFoundException(UserCartNotFoundException ex) {
        log.error(ex.getMessage());

        return ResponseExceptionProvider.buildResponse(
                HttpStatus.BAD_REQUEST,
                "User cart was not found",
                List.of(ex.getMessage())

        );

    }

    @ExceptionHandler(RequestDoesNotContainsHeader.class)
    public ResponseEntity<BaseExceptionResponse> handleRequestDoesNotContainsHeader(RequestDoesNotContainsHeader ex) {
        log.warn(ex.getMessage());

        return ResponseExceptionProvider.buildResponse(
                HttpStatus.BAD_REQUEST,
                "request does not contains header",
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
