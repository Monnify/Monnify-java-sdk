package com.monnify.exceptions;

public class DuplicateInitializationException extends RuntimeException {
    public DuplicateInitializationException(String message) {
        super(message);
    }
}
