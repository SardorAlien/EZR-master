package com.sendi.v1.service;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LearnServiceImpl implements LearnService {
    private final FlashcardService flashcardService;

    @Override
    public List<FlashcardDTO> beginLearning(DeckDTO deckDTO) {
        return Collections.emptyList();
    }
}
