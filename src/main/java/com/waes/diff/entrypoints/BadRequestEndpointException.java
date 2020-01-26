package com.waes.diff.entrypoints;

public class BadRequestEndpointException extends EndpointException {
    public static final String BAD_REQUEST = "bad_request";

    public BadRequestEndpointException(String errorMessage) {
        super(BAD_REQUEST, errorMessage);
    }

    public BadRequestEndpointException(String errorMessage, Throwable cause) {
        super(errorMessage, cause, BAD_REQUEST);
    }

    public BadRequestEndpointException(Throwable cause) {
        super(cause, BAD_REQUEST);
    }
}
