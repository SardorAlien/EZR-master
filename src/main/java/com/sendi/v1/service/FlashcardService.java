package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;

import java.util.List;

public interface FlashcardService {
    List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deck);
//    List<Flashcard> getFlashcardsByDeck(Long deckId);
}
