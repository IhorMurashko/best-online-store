package com.bestStore.authService.exceptions;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionConstantMessage {
    public String PASSWORDS_DONT_MATCH = "passwords don't match";
    public String REGISTRATION_EXCEPTION = "registration failed";
    public String USER_NOT_FOUND = "user with %s %s wasn't found";
    public String USER_ACCOUNT_IS_EXPIRED = "user account is expired";
    public String USER_ACCOUNT_HAS_ALREADY_EXIST = "user account has already exists";
    public String INVALID_ARGUMENT = "invalid argument %s";
    public String SERVICE_UNAVAILABLE = "service %s unavailable";
    public String INTERNAL_SERVER_ERROR = "internal server error: %s";
    public String USER_PASSWORD_IS_EMPTY = "user record is corrupted: no password hash";
}
