package com.waes.diff.core.exceptions;

/**
 * Exception used for return a not found with the according status code to the client
 */
public class NotFoundException extends APIException {

    public static final String NOT_FOUND = "not_found";

    public NotFoundException(String errorMessage) {
        super(404, NOT_FOUND, errorMessage);
    }

}
