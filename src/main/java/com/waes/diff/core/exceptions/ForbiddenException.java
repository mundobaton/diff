package com.waes.diff.core.exceptions;

/**
 * Exception used for return a forbidden error with the according status code to the client
 */
public class ForbiddenException extends APIException {

    public static final String FORBIDDEN = "forbidden";

    public ForbiddenException(String errorMessage) {
        super(403, FORBIDDEN, errorMessage);
    }

}
