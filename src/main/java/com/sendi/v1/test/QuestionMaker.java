package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.repo.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionMaker {
    private final FlashcardRepository flashcardRepository;

    public List<Flashcard> getFlashcards(long deckId) {
        return flashcardRepository.findAllByDeckId(deckId);
    }

    public List<Flashcard> getFlashcardsAndShuffleByQuestionCount(long deckId, int questionCount) {
        List<Flashcard> flashcards = getFlashcards(deckId);
        Collections.shuffle(flashcards);
        return flashcards.subList(0, questionCount);
    }
}
