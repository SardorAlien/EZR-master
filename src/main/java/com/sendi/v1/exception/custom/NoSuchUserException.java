package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;

public class NoSuchUserException extends NoSuchObjectException {
    private static final long serialVersionUID = -8367190938595794849L;

    public NoSuchUserException(String username) {
        super(ErrorMessages.NO_SUCH_USER.getMessage() + username);
    }

    public NoSuchUserException(long id) {
        super(ErrorMessages.NO_SUCH_USER_ID.getMessage() + id);
    }
}
