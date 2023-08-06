package com.sendi.v1.security.authmanagers;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.util.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlashcardAuthenticationManager {
    private final DeckRepository deckRepository;
    public boolean idMatchesFlashcard(Authentication authentication, Long deckId) {
        User authenticatedUser = (User) authentication.getPrincipal();

        if (deckRepository.existsById(deckId)) {
            return deckRepository.findById(deckId).get().getUser().getId().equals(authenticatedUser.getId());
        }

        throw new NoSuchDeckException(deckId);
    }
}
