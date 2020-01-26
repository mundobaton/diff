package com.waes.diff.entrypoints;

public class NotFoundEndpointException extends EndpointException {

    public static final String NOT_FOUND = "not_found";

    public NotFoundEndpointException(String errorMessage) {
        super(NOT_FOUND, errorMessage);
    }

    public NotFoundEndpointException(String errorMessage, Throwable cause) {
        super(errorMessage, cause, NOT_FOUND);
    }

    public NotFoundEndpointException(Throwable cause) {
        super(cause, NOT_FOUND);
    }

}
