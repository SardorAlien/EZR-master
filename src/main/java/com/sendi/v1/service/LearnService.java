package com.sendi.v1.service;

import com.sendi.v1.dto.FlashcardDTO;

import java.util.List;

public interface LearnService {
    List<FlashcardDTO> beginLearningSession(Long deckId);

    List<FlashcardDTO> finishLearningSession(List<FlashcardDTO> flashcardDTOs);
}
