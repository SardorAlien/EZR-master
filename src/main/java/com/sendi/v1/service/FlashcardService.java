package com.sendi.v1.service;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;

import java.util.List;

public interface FlashcardService {
    FlashcardDTO getDeckById(Long flashcardId);

    List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deck);

    List<FlashcardDTO> getFlashcardsByDeckId(Long deckId);

    FlashcardDTO createOrUpdateFlashcard(Long deckId, FlashcardDTO flashcardDTO);

    void deleteById(Long flashcardId);
}
