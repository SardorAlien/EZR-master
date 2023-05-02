package com.sendi.v1.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchRoleException extends RuntimeException {
    private static final long serialVersionUID = 5236152620302275653L;

    public NoSuchRoleException(String message) {
        super(message);
    }
}
