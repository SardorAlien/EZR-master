package com.sendi.v1.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchAuthorityException extends RuntimeException {
    private static final long serialVersionUID = -2745656796321435768L;

    public NoSuchAuthorityException(String message) {
        super(message);
    }
}
