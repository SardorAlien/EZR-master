package com.sendi.v1.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicationException extends RuntimeException {
    private static final long serialVersionUID = -6478775732142315011L;

    public DuplicationException() {
    }

    public DuplicationException(String message) {
        super(message);
    }
}
