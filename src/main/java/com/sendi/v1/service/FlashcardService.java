package com.sendi.v1.service;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlashcardService {
    FlashcardDTO getOneById(Long flashcardId);

//    FlashcardDTO getFlashcardById(Long flashcardId);

    List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deck);

    List<FlashcardDTO> getFlashcardsByDeckId(Long deckId);

    List<FlashcardDTO> getFlashcardsByDeckId(Long deckId, Pageable pageable);

    List<FlashcardDTO> getFlashcardsByDeckId(Long deckId, int page, int size);

    FlashcardDTO createOrUpdate(Long deckId, FlashcardDTO flashcardDTO);

    void deleteById(Long flashcardId);
}
