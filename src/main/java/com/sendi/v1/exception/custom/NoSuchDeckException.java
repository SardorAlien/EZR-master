package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;

public class NoSuchDeckException extends NoSuchObjectException {
    private static final long serialVersionUID = 3983659906045426939L;

    public NoSuchDeckException(long id) {
        super(ErrorMessages.NO_SUCH_DECK_ID.getMessage() + id);
    }
}
