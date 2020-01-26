package com.waes.diff.core.repositories;

public class RepositoryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public RepositoryException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public RepositoryException(Throwable cause) {
        super(cause);
    }


    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public RepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
