package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.repo.FlashcardRepository;
import com.sendi.v1.service.model.FlashcardImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearnServiceImpl implements LearnService {
    private final FlashcardService flashcardService;
    private final FlashcardRepository flashcardRepo;
    private final DeckRepository deckRepo;

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardImageDTO> beginLearningSession(Long deckId) {
        if (isDeckFinished(deckId)) {
            return getAllFlashcards(deckId);
        }

        return getNotLearnedFlashcards(deckId);
    }

    private List<FlashcardImageDTO> getAllFlashcards(Long deckId) {
        List<FlashcardImageDTO> flashcardDTOS = flashcardService.getFlashcardsByDeckId (deckId);

        return flashcardDTOS;
    }

    private List<FlashcardImageDTO> getNotLearnedFlashcards(Long deckId) {
        List<FlashcardImageDTO> flashcardDTOS = flashcardService.getFlashcardsByDeckId(deckId)
                .stream()
                .filter(t -> !t.getFlashcardDTO().isLearned())
                .collect(Collectors.toList());

        return flashcardDTOS;
    }

    private boolean isDeckFinished(Long deckId) {
        return getNotLearnedFlashcards(deckId).size() == 0;
    }

    @Override
    public void finishLearningSession(Long deckId, List<Long> flashcardIds) {
        Optional<Deck> optionalDeck = deckRepo.findById(deckId);

        if (optionalDeck.isEmpty()) return;

        Deck deck = optionalDeck.get();

        List<Flashcard> flashcards = flashcardRepo
                .findAllByDeck(deck);

        flashcards
                .stream()
                .filter(flashcard -> flashcardIds.contains(flashcard.getId()))
                .forEach(flashcard -> {
                    flashcard.setIsLearned(true);
                    flashcardRepo.save(flashcard);
                });
        }
}
