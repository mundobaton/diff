package com.waes.diff.core.exceptions;

/**
 * Exception used for return a bad request with the according status code to the client
 */
public class BadRequestException extends APIException {

    public static final String BAD_REQUEST = "bad_request";

    public BadRequestException(String errorMessage) {
        super(400, BAD_REQUEST, errorMessage);
    }

    public BadRequestException(String errorMessage, Throwable cause) {
        super(errorMessage, cause, 400, BAD_REQUEST);
    }

    public BadRequestException(Throwable cause) {
        super(cause, 400, BAD_REQUEST);
    }

}
