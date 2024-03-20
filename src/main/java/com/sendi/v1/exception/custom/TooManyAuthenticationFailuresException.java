package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooManyAuthenticationFailuresException extends RuntimeException {
    private static final long serialVersionUID = 6383628235832295153L;

    public TooManyAuthenticationFailuresException() {

    }

    public TooManyAuthenticationFailuresException(String username) {
        super(ErrorMessages.TOO_MANY_AUTH_FAIL.getMessage() + username);
    }
}
