package com.sendi.v1.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchObjectException extends RuntimeException {

    private static final long serialVersionUID = -3198217720909058603L;

    public NoSuchObjectException() {
    }

    public NoSuchObjectException(String message) {
        super(message);
    }
}
