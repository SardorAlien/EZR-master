package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.service.dto.FlashcardDTO;
import com.sendi.v1.service.dto.mapper.FlashcardMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.repo.FlashcardRepository;
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
    private final FlashcardMapper flashcardMapper;
    private final DeckRepository deckRepo;

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardDTO> beginLearningSession(Long deckId) {
        if (isDeckFinished(deckId)) {
            return getAllFlashcards(deckId);
        }

        return getNotLearnedFlashcards(deckId);
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
