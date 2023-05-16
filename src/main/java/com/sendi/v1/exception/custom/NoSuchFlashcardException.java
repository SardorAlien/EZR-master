package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;

public class NoSuchFlashcardException extends NoSuchObjectException {
    private static final long serialVersionUID = 3983659906045426939L;

    public NoSuchFlashcardException(long id) {
        super(ErrorMessages.NO_SUCH_FLASHCARD_ID.getMessage() + id);
    }
}
