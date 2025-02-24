package com.monnify.exceptions;

public class MonnifyException extends RuntimeException {
    public MonnifyException(String message) {
        super(message);
    }
    public MonnifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MonnifyException(Throwable cause) {
        super(cause);
    }
}
