package com.sendi.v1.service;

import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.service.model.FlashcardImageDTO;

import java.util.List;

public interface LearnService {
    List<FlashcardImageDTO> beginLearningSession(Long deckId);
    void finishLearningSession(Long deckId, List<Long> flashcardIds);
}

