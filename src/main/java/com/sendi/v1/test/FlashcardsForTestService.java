package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.repo.FlashcardRepository;
import com.sendi.v1.util.Randomizing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlashcardsForTestService {
    private final FlashcardRepository flashcardRepository;
    private List<Flashcard> flashcards;
    private int actualSizeFlashcards;

    public List<Flashcard> receiveFlashcardsByDeckId(long deckId) {
        flashcards = flashcardRepository.findAllByDeckId(deckId);
        return flashcards;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public List<Flashcard> getFlashcardsByQuestionCountAndShuffle(long deckId, int questionCount) {
        receiveFlashcardsByDeckId(deckId);
        shuffleFlashcards();
        flashcards = getFlashcardsByQuestionCount(questionCount);
        actualSizeFlashcards = flashcards.size();
        return flashcards;
    }

    private void shuffleFlashcards() {
        Collections.shuffle(flashcards);
    }

    private List<Flashcard> getFlashcardsByQuestionCount(int questionCount) {
        return flashcards.subList(0, questionCount);
    }

    public Flashcard getRandomFlashcardAndRemoveFromList() {
        int randomFlashcardId = Randomizing.getRandomNumber(flashcards.size());

        Flashcard flashcard = flashcards.get(randomFlashcardId);
        flashcards.remove(randomFlashcardId);

        return flashcard;
    }

    public int getActualSizeFlashcards() {
        return actualSizeFlashcards;
    }

    public Optional<Flashcard> getByFlashcardId(long flashcardId) {
        return flashcardRepository.findById(flashcardId);
    }
}
