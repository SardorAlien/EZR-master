package com.sendi.v1.service;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;

import java.util.List;

public interface LearnService {
    List<FlashcardDTO> beginLearning(DeckDTO deckDTO);
}
