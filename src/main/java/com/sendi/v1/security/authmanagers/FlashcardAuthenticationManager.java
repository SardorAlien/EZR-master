package com.sendi.v1.security.authmanagers;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.security.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class FlashcardAuthenticationManager {

    public boolean idMatchesFlashcard(Authentication authentication, Long deckId) {
        User authenticatedUser = (User) authentication.getPrincipal();

        return authenticatedUser.getDecks()
                .stream()
                .map(Deck::getFlashcards)
                .flatMap(flashcards -> flashcards
                        .stream()
                        .map(flashcard -> flashcard.getDeck().getId()))
                .anyMatch(id -> id.equals(deckId));
    }
}
