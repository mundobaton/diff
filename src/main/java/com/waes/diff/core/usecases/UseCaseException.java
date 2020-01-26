package com.waes.diff.core.usecases;

import lombok.Getter;

public class UseCaseException extends RuntimeException {


    private static final long serialVersionUID = 1L;
    @Getter
    private Integer errorCode;

    /**
     * @param message
     */
    public UseCaseException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public UseCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message The message that indicates what failed
     * @param code    The error code that indicates which validation failed
     */
    public UseCaseException(final String message, final Integer code) {
        super(message);
        this.errorCode = code;

    }

    /**
     * @param message The message that indicates what failed
     * @param cause
     * @param code    The error code that indicates which validation failed
     */
    public UseCaseException(final String message, final Throwable cause, final Integer code) {
        super(message, cause);
        this.errorCode = code;
    }
}
