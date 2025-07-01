package com.bestStore.authService.exceptions.exception;

public class InternalServerErrorException extends  RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
