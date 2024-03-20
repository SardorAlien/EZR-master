package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserDuplicationException extends DuplicationException {
    private static final long serialVersionUID = -4569608452146156266L;

    public UserDuplicationException(String usernameOrEmail) {
        super(ErrorMessages.USER_DUPLICATION.getMessage() + usernameOrEmail);
    }
}
