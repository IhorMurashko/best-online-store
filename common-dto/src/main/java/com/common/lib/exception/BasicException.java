package com.common.lib.exception;
/**
 * Abstract base class for custom application exceptions.
 * Allows defining consistent structure for error message and HTTP status.
 *
 * @author Ihor Murashko
 */
public abstract class BasicException extends RuntimeException {

    public BasicException(String message) {
        super(message);
    }

    public abstract int getHttpStatus();


}
