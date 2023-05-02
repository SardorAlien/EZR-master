package com.sendi.v1.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleDuplicationException extends RuntimeException {
    private static final long serialVersionUID = -7255663927912634937L;

    public RoleDuplicationException(String message) {
        super(message);
    }
}
