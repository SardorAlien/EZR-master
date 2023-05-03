package com.sendi.v1.exception.custom;

public class NoSuchUserException extends RuntimeException {
    private static final long serialVersionUID = -8367190938595794849L;

    public NoSuchUserException(String message) {
        super(message);
    }
}
