package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FlashcardRepository extends PagingAndSortingRepository<Flashcard, Long> {
    List<Flashcard> findAllByDeck(Deck deck);
    List<Flashcard> findAllByDeck(Deck deck, Pageable pageable);
    void deleteFlashcardById(Long flashcardId);

}
