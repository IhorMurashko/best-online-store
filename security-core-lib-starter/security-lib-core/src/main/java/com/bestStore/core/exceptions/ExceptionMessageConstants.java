package com.bestStore.core.exceptions;

public class ExceptionMessageConstants {
    private ExceptionMessageConstants() {

    }

    public static final String TOKEN_DOESNT_HAVE_REQUIRED_CLAIM
            = "token doesn't have required claim: %s";

    public static final String HEADER_DOESNT_HAVE_REQUIRED_RAW
            = "header doesn't have required raw: %s";

    public static final String REQUEST_DOESNT_HAVE_REQUIRED_HEADER
            = "request doesn't have required header: %s";


    public static final String TOKEN_IS_NOT_VALID
            = "token is not valid";
    public static final String TOKEN_WAS_REVOKED
            = "token was revoked";
    public static final String INVALID_TOKEN_TYPE
            = "invalid token type %s";


    public static final String ACCESS_DENIED_EXCEPTION_MESSAGE
            = "Error: Access denied";
    public final static String UNAUTHORIZED_EXCEPTION_MESSAGE
            = "Error: Unauthorized";


}
