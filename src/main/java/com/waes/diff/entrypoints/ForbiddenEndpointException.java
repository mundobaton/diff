package com.waes.diff.entrypoints;

public class ForbiddenEndpointException extends EndpointException {

    public static final String FORBIDDEN = "forbidden";

    public ForbiddenEndpointException(String errorMessage) {
        super(FORBIDDEN, errorMessage);
    }

    public ForbiddenEndpointException(String errorMessage, Throwable cause) {
        super(errorMessage, cause, FORBIDDEN);
    }

    public ForbiddenEndpointException(Throwable cause) {
        super(cause, FORBIDDEN);
    }

}
