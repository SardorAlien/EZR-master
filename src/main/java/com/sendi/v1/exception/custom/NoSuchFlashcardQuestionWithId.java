package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;

public class NoSuchFlashcardQuestionWithId extends NoSuchObjectException {
    private static final long serialVersionUID = -8562579210189937958L;

    public NoSuchFlashcardQuestionWithId(String exceptionStr) {
        super(ErrorMessages.NO_SUCH_FLASHCARD_WITH_ID.getMessage() + exceptionStr);
    }
}
