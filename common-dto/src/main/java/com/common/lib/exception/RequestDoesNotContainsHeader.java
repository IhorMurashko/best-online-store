package com.common.lib.exception;

public class RequestDoesNotContainsHeader extends BasicException {
    private int httpStatus;

    public RequestDoesNotContainsHeader(String message) {
        super(message);
    }

    public RequestDoesNotContainsHeader(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    @Override
    public int getHttpStatus() {
        return this.httpStatus;
    }

}
