package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;

public class NoSuchAuthorityException extends NoSuchObjectException {
    private static final long serialVersionUID = -2745656796321435768L;

    public NoSuchAuthorityException(String permission) {
        super(ErrorMessages.NO_SUCH_AUTHORITY.getMessage() + permission);
    }

    public NoSuchAuthorityException(long id) {
        super(ErrorMessages.NO_SUCH_AUTHORITY_ID.getMessage() + id);
    }
}
