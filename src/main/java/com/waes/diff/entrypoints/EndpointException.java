package com.waes.diff.entrypoints;

public class EndpointException extends RuntimeException {

    private final String errorCode;


    /**
     * Represent a not successful response.
     *
     * @param errorCode
     * @param errorMessage
     */
    public EndpointException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }


    /**
     * Represent a not successful response.
     *
     * @param message
     * @param cause
     * @param errorCode
     */
    public EndpointException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Represent a not successful response.
     *
     * @param cause
     * @param errorCode
     */
    public EndpointException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     * @return the error code to return in the response.
     */
    public String getErrorCode() {
        return errorCode;
    }

}
