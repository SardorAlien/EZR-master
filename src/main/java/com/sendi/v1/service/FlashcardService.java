package com.sendi.v1.service;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;

import java.util.List;

public interface FlashcardService {
    List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deck);
    List<FlashcardDTO> getFlashcardsByDeckId(Long deckId);
    FlashcardDTO createOrUpdateDeck(FlashcardDTO flashcardDTO);

    void deleteById(Long flashcardId);

    FlashcardDTO getDeckById(Long flashcardId);
}
