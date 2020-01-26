package com.waes.diff.core.exceptions;

public class APIException extends RuntimeException {

    private final Integer statusCode;
    private final String errorCode;


    /**
     * Represent a not successful response.
     *
     * @param statusCode
     * @param errorCode
     * @param errorMessage
     */
    public APIException(Integer statusCode, String errorCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }


    /**
     * Represent a not successful response.
     *
     * @param message
     * @param cause
     * @param statusCode
     * @param errorCode
     */
    public APIException(String message, Throwable cause, Integer statusCode, String errorCode) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    /**
     * Represent a not successful response.
     *
     * @param cause
     * @param statusCode
     * @param errorCode
     */
    public APIException(Throwable cause, Integer statusCode, String errorCode) {
        super(cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    /**
     * @return the status code to return in the response.
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @return the error code to return in the response.
     */
    public String getErrorCode() {
        return errorCode;
    }

}
