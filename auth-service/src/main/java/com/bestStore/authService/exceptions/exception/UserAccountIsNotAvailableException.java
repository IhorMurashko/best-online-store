package com.bestStore.authService.exceptions.exception;

public class UserAccountIsNotAvailableException extends RuntimeException {
    public UserAccountIsNotAvailableException(String message) {
        super(message);
    }
}
