package com.sendi.v1.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDuplicationException extends RuntimeException {
    private static final long serialVersionUID = -4569608452146156266L;
    
    public UserDuplicationException(String message) {
        super(message);
    }
}
