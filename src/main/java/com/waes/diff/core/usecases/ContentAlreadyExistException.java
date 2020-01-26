package com.waes.diff.core.usecases;

/**
 * Exception used to indicate when a content is already present
 */
public class ContentAlreadyExistException extends UseCaseException {

    public ContentAlreadyExistException(String message) {
        super(message);
    }

}
