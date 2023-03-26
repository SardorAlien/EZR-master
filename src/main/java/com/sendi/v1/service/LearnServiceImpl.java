package com.sendi.v1.service;

import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.repo.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearnServiceImpl implements LearnService {
    private final FlashcardService flashcardService;
    private final FlashcardRepository flashcardRepo;

    @Override
    public List<FlashcardDTO> beginLearningSession(Long deckId) {
        if (isDeckFinished(deckId)) {
            return getAllFlashcards(deckId);
        }

        return getNotLearnedFlashcards(deckId);
    }

    @Override
    public void finishLearningSession(List<FlashcardDTO> flashcardDTOs) {

    }

    private List<FlashcardDTO> getAllFlashcards(Long deckId) {
        List<FlashcardDTO> flashcardDTOs = flashcardService.getFlashcardsByDeckId(deckId);

        return flashcardDTOs;
    }

    private List<FlashcardDTO> getNotLearnedFlashcards(Long deckId) {
        List<FlashcardDTO> flashcardDTOs = flashcardService.getFlashcardsByDeckId(deckId)
                .stream()
                .filter(t -> !t.isLearned())
                .collect(Collectors.toList());

        return flashcardDTOs;
    }

    private boolean isDeckFinished(Long deckId) {
        if (getNotLearnedFlashcards(deckId).size() > 0) {
            return false;
        }

        return true;
    }

    @Override
    public void finishLearningSession(Long deckId, List<Long> learnedFlashcardsId) {

        flashcardRepo.updateLearnedStateOfFlashcardsByDeckId(deckId, learnedFlashcardsId, true);
    }

//    @Override
//    public List<FlashcardDTO> finishLearningSession(List<FlashcardDTO> flashcardDTOs) {
//
//        List<FlashcardDTO> newFlashcardDTOs = flashcardDTOs.stream()
//                                                            .map(flashcardService::createOrUpdateFlashcard)
//                                                            .collect(Collectors.toList());
//        return Collections.emptyList();
//    }
}
