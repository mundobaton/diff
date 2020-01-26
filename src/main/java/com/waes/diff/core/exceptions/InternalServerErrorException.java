package com.waes.diff.core.exceptions;

/**
 * Exception used for return an internal server error with the according status code to the client
 */
public class InternalServerErrorException extends APIException {

    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";

    public InternalServerErrorException(String errorMessage) {
        super(500, INTERNAL_SERVER_ERROR, errorMessage);
    }


    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause, 500, INTERNAL_SERVER_ERROR);
    }


    public InternalServerErrorException(Throwable cause) {
        super(cause, 500, INTERNAL_SERVER_ERROR);
    }

}
