package com.sendi.v1.service;

import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.service.model.FlashcardDTORepresentable;
import com.sendi.v1.service.model.FlashcardImageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FlashcardService {
    FlashcardDTORepresentable getOneById(Long flashcardId);

    List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deck);

    List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId);

    List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId, Pageable pageable);

    List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId, int page, int size);

    FlashcardImageDTO createOrUpdate(Long deckId, FlashcardDTO flashcardDTO, MultipartFile img) throws IOException;
    FlashcardDTO createOrUpdate(Long deckId, FlashcardDTO flashcardDTO) throws IOException;

    void deleteById(Long flashcardId);
}
