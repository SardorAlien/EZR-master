package com.sendi.v1.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthorityDuplicationException extends RuntimeException {
    private static final long serialVersionUID = 7960411046132837102L;

    public AuthorityDuplicationException(String message) {
        super(message);
    }
}
