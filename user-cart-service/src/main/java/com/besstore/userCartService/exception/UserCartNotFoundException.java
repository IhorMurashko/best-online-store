package com.besstore.userCartService.exception;

public class UserCartNotFoundException extends RuntimeException {
    public UserCartNotFoundException(String message) {
        super(message);
    }
}
