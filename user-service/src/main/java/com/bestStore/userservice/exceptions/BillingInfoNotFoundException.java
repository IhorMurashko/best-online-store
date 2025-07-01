package com.bestStore.userservice.exceptions;

public class BillingInfoNotFoundException extends RuntimeException {
    public BillingInfoNotFoundException(String message) {
        super(message);
    }
}
