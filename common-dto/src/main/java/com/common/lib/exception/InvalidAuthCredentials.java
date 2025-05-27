package com.common.lib.exception;


/**
 * Exception thrown when authentication credentials are invalid.
 * Used in authentication scenarios.
 *
 * @author Ihor Murashko
 */

public class InvalidAuthCredentials extends BasicException {


    private int httpStatus;

    public InvalidAuthCredentials(String message) {
        super(message);
    }

    public InvalidAuthCredentials(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    @Override
    public int getHttpStatus() {
        return this.httpStatus;
    }
}
