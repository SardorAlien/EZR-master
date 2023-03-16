package com.sendi.v1.service;

import com.sendi.v1.dto.FlashcardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearnServiceImpl implements LearnService {
    private final FlashcardService flashcardService;

    @Override
    public List<FlashcardDTO> beginLearningSession(Long deckId) {
        List<FlashcardDTO> flashcardDTOs = flashcardService.getFlashcardsByDeckId(deckId)
                                                            .stream()
                                                            .filter(t -> !t.isLearned())
                                                            .collect(Collectors.toList());
        return flashcardDTOs;
    }

    @Override
    public List<FlashcardDTO> finishLearningSession(List<FlashcardDTO> flashcardDTOs) {
        List<FlashcardDTO> newFlashcardDTOs = flashcardDTOs.stream()
                                                            .map(flashcardService::createOrUpdateDeck)
                                                            .collect(Collectors.toList());
        return newFlashcardDTOs;
    }
}
