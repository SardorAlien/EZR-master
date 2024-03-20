package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AuthorityDuplicationException extends DuplicationException {
    private static final long serialVersionUID = 7960411046132837102L;

    public AuthorityDuplicationException(String permission) {
        super(ErrorMessages.AUTHORITY_DUPLICATION.getMessage() + permission);
    }
}
