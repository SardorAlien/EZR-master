package com.sendi.v1.service;

import com.sendi.v1.dto.FlashcardDTO;

import java.util.List;

public interface LearnService {
    List<FlashcardDTO> beginLearningSession(Long deckId);

    void finishLearningSession(List<FlashcardDTO> flashcardDTOs);
    void finishLearningSession(Long deckId, List<Long> learnedFlashcardsId);
}

