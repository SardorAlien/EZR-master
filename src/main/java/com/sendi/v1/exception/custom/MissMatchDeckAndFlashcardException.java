package com.sendi.v1.exception.custom;

import com.sendi.v1.util.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissMatchDeckAndFlashcardException extends RuntimeException {
    private static final long serialVersionUID = 7660196820536290816L;

    public MissMatchDeckAndFlashcardException(String message) {
        super(message);
    }

    public MissMatchDeckAndFlashcardException(long deckId) {
        super(ErrorMessages.MISS_MATCH_DECK_FLASHCARD.getMessage() + deckId);
    }
}
